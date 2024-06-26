package michelavivacqua.gestionedispositivilogin.controllers;

import michelavivacqua.gestionedispositivilogin.exceptions.BadRequestException;
import michelavivacqua.gestionedispositivilogin.payloads.DipendenteLoginRespDTO;
import michelavivacqua.gestionedispositivilogin.payloads.DipendenteLoginDTO;
import michelavivacqua.gestionedispositivilogin.payloads.NewDipendenteDTO;
import michelavivacqua.gestionedispositivilogin.payloads.NewDipendenteRespDTO;
import michelavivacqua.gestionedispositivilogin.services.AuthService;
import michelavivacqua.gestionedispositivilogin.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendentiService dipendentiService;

    @PostMapping("/login")
    public DipendenteLoginRespDTO login(@RequestBody DipendenteLoginDTO payload){
        return new DipendenteLoginRespDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO saveUser(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation){
        // @Validated valida il payload in base ai validatori utilizzati nella classe NewUserDTO
        // BindingResult validation ci serve per valutare il risultato di questa validazione
        if(validation.hasErrors()) { // Se ci sono stati errori di validazione dovrei triggerare un 400 Bad Request
            throw new BadRequestException(validation.getAllErrors()); // Inviamo la lista degli errori all'Error Handler opportuno
        }
        // Altrimenti se non ci sono stati errori posso salvare tranquillamente lo user
        return new NewDipendenteRespDTO(this.dipendentiService.saveDipendente(body).getId());
    }

}
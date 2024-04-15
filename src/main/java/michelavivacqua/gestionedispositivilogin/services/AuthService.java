package michelavivacqua.gestionedispositivilogin.services;

import michelavivacqua.gestionedispositivilogin.entities.Dipendente;
import michelavivacqua.gestionedispositivilogin.payloads.DipendenteLoginDTO;
import michelavivacqua.gestionedispositivilogin.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import michelavivacqua.gestionedispositivilogin.exceptions.UnauthorizedException;

@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(DipendenteLoginDTO payload){
        // 1. Controllo le credenziali
        // 1.1 Cerco nel db tramite l'email l'utente
        Dipendente dipendente = this.dipendentiService.findByEmail(payload.email());
        // 1.2 Verifico se la password combacia con quella ricevuta nel payload
        if(dipendente.getPassword().equals(payload.password())) {
            // 2. Se è tutto OK, genero un token e lo torno
            return jwtTools.createToken(dipendente);
        } else {
            // 3. Se le credenziali invece non fossero OK --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }


    }
}

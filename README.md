# TnT Dropbox
Projekt TnT Dropbox je zasnovan za reševanje težav pri varnem in učinkovitem shranjevanju, deljenju in upravljanju datotek v digitalnem okolju. 
Uporabniki pogosto potrebujejo platformo, ki omogoča preprosto sodelovanje, sledenje različicam in nadzor nad dostopom do datotek, hkrati pa zagotavlja varnost in enostaven dostop kjerkoli in kadarkoli.
Naša rešitev ponuja robustno aplikacijo, ki temelji na mikrostoritvah, z naprednimi funkcijami za shranjevanje ter upravljanje datotek, 
obvestila, sledenje aktivnosti, plačilne sisteme in sodelovanje, kar uporabnikom omogoča nemoteno upravljanje podatkov in učinkovito sodelovanje.
## Načrt mikrostoritev in REST API končnih točk
- **User Service:**
    - `GET /users` – Vrne seznam vseh uporabniških profilov. (samo admin)
    - `GET /users/{id}` – Vrne podatke o določenem uporabniku glede na ID.
    - `POST /users/register` – Ustvari nov uporabniški profil (registracija).
    - `POST /users/login` – Prijava uporabnika v sistem.
    - `PUT /users/{id}` – Posodobi podatke obstoječega uporabnika glede na ID. (sprememba gesla, ...)
    - `DELETE /users/{id}` – Izbriše uporabniški profil na podlagi ID-ja.
  
- **File Service:**
    - `GET /files/user/{userId}` – Vrne seznam vseh datotek (prijavljenega uporabnika).
    - `GET /files/group/{GrId}` – Vrne seznam vseh datotek določene skupine uporabnikov.
    - `GET /files/{id}` – Vrne datoteko glede na ID.
    - `POST /files` – Shrani novo datoteko.
    - `POST /files/{fileId}/groups/{GrId}` – Shrani datoteko v določeno skupino.
    - `PUT /files/{id}` – Posodobi datoteko.
    - `DELETE /files/{id}` – Izbriši datoteko.
  
- **Group Service:**
    - `GET /groups` – Vrne seznam vseh skupin (prijavljenega uporabnika).
    - `GET /groups/{id}` – Vrne podatke o posamezni skupini.
    - `GET /groups/{id}/members` – Vrne člane posamezne skupine.
    - `POST /groups` – Ustvari skupino.
    - `PUT /groups/{id}` – Posodobi podatke o skupini.
    - `PUT /groups/{id}/add/{userId}` – doda novega uporabnika v skupino.
    - `DELETE /groups/{id}` – Izbriši skupino.
    - `DELETE /groups/{id}/remove/{id}` – odstrani uporabnika iz skupine.
  
- **Comment Service:**
    - `GET /comments/group/{GrId}` – Vrne seznam komentarjev določene skupine.
    - `POST /comments/group/{GrId}` – Dodaj komentar v skupino.
    - `PUT /comments/{id}` – Uredi komentar.
    - `DELETE /comments/{id}` – Izbriši komentar.
  
- **Notification Service:**
    - `GET /notifications` – Vrne seznam obvestil (prijavljenega uporabnika).
    - `GET /notifications/{id}` – Vrne vsebino obvestila.
    - `POST /notifications/{UserID}` – Pošlji obvestilo določenemu uporabniku (admin / mikrostoritev).
    - `DELETE /notifications` – Izbriši vsa prejeta obvestila uporabnika.
    - `DELETE /notifications/{id}` – Izbriši določeno obvestilo.
  
- **History Service:**
    - `GET /logs/user/{id}` – Vrne seznam zapisov dogodkov določenega uporabnika.
    - `GET /logs/{id}` – Podrobnosti posameznega zapisa.
    - `POST /logs` – Ustvari nov zapis.

## Načrt baze podatkov
![Načrt baze podatkov](assets/ER.png "Načrt baze podatkov")

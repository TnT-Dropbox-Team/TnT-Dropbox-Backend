# TnT Dropbox
Projekt TnT Dropbox je zasnovan za reševanje težav pri varnem in učinkovitem shranjevanju, deljenju in upravljanju datotek v digitalnem okolju. 
Uporabniki pogosto potrebujejo platformo, ki omogoča preprosto sodelovanje, sledenje različicam in nadzor nad dostopom do datotek, hkrati pa zagotavlja varnost in enostaven dostop kjerkoli in kadarkoli.
Naša rešitev ponuja robustno aplikacijo, ki temelji na mikrostoritvah, z naprednimi funkcijami za shranjevanje ter upravljanje datotek, 
obvestila, sledenje aktivnosti, plačilne sisteme in sodelovanje, kar uporabnikom omogoča nemoteno upravljanje podatkov in učinkovito sodelovanje.
## Načrt mikrostoritev in REST API končnih točk
- **User Service:**
    - `GET /users` – Vrne seznam vseh uporabniških profilov. (samo admin)
    - `GET /users/{id}` – Vrne podatke o določenem uporabniku glede na ID.
    - `POST /users` – Ustvari nov uporabniški profil (registracija).
    - `POST /users/login` – Prijava uporabnika v sistem.
    - `PUT /users/{id}` – Posodobi podatke obstoječega uporabnika glede na ID. (sprememba gesla, ...)
    - `DELETE /users/{id}` – Izbriše uporabniški profil na podlagi ID-ja.
  
- **File Service:**
    - `GET /files` – Vrne seznam vseh datotek (prijavljenega uporabnika).
    - `GET /files/group/{GrId}` – Vrne seznam vseh datotek določene skupine uporabnikov.
    - `GET /files/{id}` – Vrne datoteko glede na ID.
    - `POST /files` – Shrani novo datoteko.
    - `POST /files/group/{GrId}` – Shrani datoteko v določeno skupino.
    - `PUT /files/{id}` – Posodobi datoteko.
    - `DELETE /files/{id}` – Izbriši datoteko.
  
- **Group Service:**
    - `GET /groups` – Vrne seznam vseh skupin (prijavljenega uporabnika).
    - `POST /groups` – Ustvari skupino.
    - `PUT /groups/{id}` – Posodobi podatke o skupini.
    - `DELETE /groups/{id}` – Izbriši skupino.
    - `DELETE /groups/{id}/leave` – Zapusti skupino.
  
- **Comment Service:**
    - `GET /comments/group/{GrId}` – Vrne seznam komentarjev določene skupine.
    - `POST /comments/group/{GrId}` – Dodaj komentar v skupino.
    - `PUT /comments/{id}` – Uredi komentar.
    - `DELETE /comments/{id}` – Izbriši komentar.
  
- **Notification Service:**
    - `GET /notifications` – Vrne seznam obvestil (prijavljenega uporabnika).
    - `GET /notifications/{id}` – Vrne vsebino obvestila.
    - `POST /notifications` – Pošlji obvestilo vsem uporabnikom (samo admin / mikrostoritev).
    - `POST /notifications/{UserID}` – Pošlji obvestilo določenemu uporabniku (admin / mikrostoritev).
    - `DELETE /notifications` – Izbriši vsa prejeta obvestila uporabnika.
    - `DELETE /notifications/{id}` – Izbriši določeno obvestilo.
  
- **History Service:**
    - `GET /logs` – Vrne seznam zapisov dogodkov.
    - `GET /logs/{id}` – Podrobnosti posameznega zapisa.
    - `POST /logs` – Ustvari nov zapis.
  
- **Payment Service** (morda kakšen zunanji API):

# TerraIncognitaServer
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/45a732454fbf4bd59a39f0bf7d96d3d0)](https://www.codacy.com/app/albertinizao/TerraIncognitaServer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=albertinizao/TerraIncognitaServer&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/albertinizao/TerraIncognitaServer/badge.svg)](https://coveralls.io/github/albertinizao/TerraIncognitaServer)
[![Build Status](https://travis-ci.org/albertinizao/TerraIncognitaServer.svg?branch=master)](https://travis-ci.org/albertinizao/TerraIncognitaServer)

Back from the Terra Incognita website

## Requisitos
### Como usuario quiero registrarme con el fin de poder acceder a la página
Los datos a guardar deben ser: Nick, password, nombres, apellidos, DNI, email, teléfono, fecha de nacimiento, alergias o asuntos médicos relevantes.

### Como usuario quiero poder modificar mis datos
Se deben poder modificar todos los datos salvo el DNI.

### Como usuario quiero poseer roles para poder acceder a determinadas características
Roles: usuario, socio, organizador

### Como organizador quiero crear partidas
Los datos que tiene son: Nombre, Fecha inicio, fecha fin, fecha inicio inscripciones, fecha fin de inscripciones, precio inscripción, localización, foto, PNJ secretos

### Como organizador quiero crear grupos de juego asociados a una partida
Los datos son: Nombre

### Como organizador quiero crear personajes asociados a un grupo de juego de una partida
Los datos son: Nombre, descripción, PJ/PNJ/Indeterminado

### Como usuario quiero inscribirme en un evento
Los datos son: Lista personajes por orden + notas, comentarios de pago, Lista de tramas buscadas, Lista de tramas rechazadas, notas, PJ/PNJ/Indeterminado

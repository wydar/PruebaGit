/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm() {
    mensaje='';
    flag_ok=true;
    var x = document.formulario.user.value;
    if (x == "") {
        mensaje = mensaje + "Nombre de usuario no puede estar vacio \n";
        flag_ok = false;
    }
    var x = document.formulario.pass.value;
    if (x == "") {
        mensaje = mensaje + "La contraseña no puede estar vacia \n";
        flag_ok = false;
    }
    if (flag_ok == false) {
        alert(mensaje);
        return false;
    } else
    return true;
}

function validateFormRegistro() {
    mensaje='';
    var x = document.formulario.user.value;
    if (x == "") {
        mensaje = mensaje + "Nombre de usuario no puede estar vacio \n";
    }
    var x = document.formulario.pass1.value;
    if (x == "") {
        mensaje = mensaje + "La contraseña no puede estar vacia \n";
    }
    
    var y = document.formulario.pass2.value;
    if(x != y && x != "" && y != ""){
        mensaje = mensaje + "Las contraseñas no coinciden \n";
    }
  
    var x = document.formulario.fecha.value;
    if(x == "" ){
        mensaje = mensaje + "La fecha no puede estar vacia \n";
    }
  
    var x = document.formulario.num.value;
    if(x == "") {
        mensaje= mensaje + "Numeo de seguridad no puede estar vacio \n";
    }

    var x = document.formulario.correo.value;
    if (x == "") {
        mensaje = mensaje + "Correo electronico no puede estar vacio \n";
    }

    if (mensaje != "") {
        alert(mensaje);
        return false;
    } else
    return true;
}



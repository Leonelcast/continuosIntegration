console.log("aaaaaa")

function precio(){
    let x = document.getElementById("cantidad_dispositivos").value; 
    let y = document.getElementById("precios").value
    console.log(x)
    console.log(y)

    let resultado =  x*y
    console.log(resultado)
    document.getElementById('resul').value = resultado.toFixed(2);
    document.getElementById('resul2').innerHTML = resultado.toFixed(2);
}






function tipoCliente(){
    console.log("buenooooo")
    let idCliente = document.getElementById("idCliente").value;
    

    let valores = []
    let idTipo =  document.getElementsByClassName("ids");
    for(var i=0;i<idTipo.length;i++){
        valores.push(idTipo[i].value)

    }
  
    let id = idCliente.toString();
    console.log("idddd")
    console.log(id)
   
    if(valores.includes(id) === true){
           console.log("siu8")
        document.getElementById('existeCliente').innerHTML = "El cliente esta registrado";
        document.getElementById("btcompra").disabled = false;
    }else{
        console.log("no")
        document.getElementById('existeCliente').innerHTML = "El cliente no esta registrado";
        document.getElementById("btcompra").disabled = true;
    }




    
      
        
    
    
    
   
       

    

}


//Para la fecha 



function sub(){
    let inicio = document.getElementById("inicioS");
    let dates = new Date(inicio.value)
    console.log(inicio.value )
    dates.setFullYear(dates.getFullYear()+ 1) ;
    console.log(dates)
    
    if(dates.getMonth() < 10){
        var mes = '0'+(dates.getMonth() +1);
    }else{
        var mes = (dates.getMonth() +1) ;

    }

    console.log(mes)
    console.log(dates.getDay())
    console.log(dates.getMonth())
 
    
    let resultado =  dates.getFullYear()+'-'+mes +'-'+ dates.getDate();
   
   
    let final = document.getElementById("finS");
    console.log(final.value)
    
    document.getElementById('finS').value = resultado ;

    



}
function credenciales(){
   
   document.getElementById("credenciales").style.display="block"
   

}

function ocultar(){
   
    document.getElementById("credenciales").style.display="none";
    document.getElementById("todo").style.display="block";
    
 
 }
 





   

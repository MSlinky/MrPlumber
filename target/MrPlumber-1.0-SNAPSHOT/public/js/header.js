/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$( document ).ready(function() {
    
    
ajax();
   
});


function ajax() {
    $.ajax({
        url: "GetDataEvent",
        type: 'POST',
        success: function (data) {
            console.log(data);
            console.log(data.data.length);
             $("#NoAlertas").html(data.data.length);
        
        },
        error: function (request, error) {
            console.log([request, error]);
        }
    });
}
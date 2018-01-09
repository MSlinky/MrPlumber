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
        url: "GetDataEventToday",
        type: 'POST',
        success: function (data) {
            data = JSON.parse(data.data);
            console.log(data.length);
             $("#NoAlertas").html(data.length);
        
        },
        error: function (request, error) {
            console.log([request, error]);
        }
    });
}
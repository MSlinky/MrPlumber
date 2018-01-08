var carrito=[];
var venta = {
    'tipo': null,
    'id': null
};
var intervalo;
var contExtern = 0;

function enviar_ajax(datos, link, callBack){
    NProgress.start();
    $.ajax({
        url: link,
        type: 'POST',
        data: datos,
        success: function(text){
            callBack(text);
            NProgress.done();
        },
        error : function (request, error) {
            console.log([request,error]);
        }
    });
}

function sendForm(form, link, callBack){
    let sendData = '';
    for (var i = 0; i < form.length; i++) {
        sendData += form[i].nombre+"="+$(form[i]).val()+"&";
    };
    enviar_ajax(sendData, link, callBack);
}

function actTablePro(response){
    $("table_productos").html("");
    $('#datatable-productos').DataTable().clear().draw();
    let info = [];
    for (var i=0; i < response.length; i++){
        info = info.concat(
            {
                0: '<button id="'+response[i]['id']+'" type="button" name="AgregarProductoConsulta" class="buttonEvent btn btn-info glyphicon glyphicon-shopping-cart"></button>',
                1:  response[i]['nombre'],
                2:  response[i]['numParte'],
                3:  response[i]['ubicacion'],
                4:  response[i]['unidad'],
                5:  response[i]['descripcion'],
                6:  response[i]['idCategoriasProductos'],
                7:  response[i]['costo'],
                8:  "% "+response[i]['precioPublico'],
                9:  "% "+response[i]['precioSocio'],
                10:  response[i]['cantidadAlmacen'],
               }
        );
    }
    $('#datatable-productos').DataTable().rows.add( info ).draw();
    $('#datatable-productos').off( "click", ".buttonEvent", buttonEventBuy );
    $('#datatable-productos').on('click','.buttonEvent', buttonEventBuy);
}
function existe(event){
    for (var i=0; i < carrito.length; i++){
        if(event.target.id==carrito[i]['id']){
            return true;
        }
    }
    return false;
}
function buttonEventBuy(event){
    if(!existe(event)){
        let table = $($(event.target).parent().parent())[0]['children'];
        carrito=carrito.concat({
                "id": event.target.id,
                "nombre": table[1].innerHTML,
                "cantidadMax": table[10].innerHTML,
                "cantidad": 1,
                "precio": table[7].innerHTML,
                "cliente": table[8].innerHTML.split('% ')[1],
                "socio": table[9].innerHTML.split('% ')[1],
                "descripcion":  table[5].innerHTML
            });
        tableBuy();
    }
}
function tableBuy(){
    if(carrito.length>0){
        $('#cancelar').show();
        $('#comprar').show();
        $('#table_carrito').show();
        let info = '';
        let cantidad=0;
        let total=0;
        for (var i=0; i < carrito.length; i++){
            info += '<tr>';
                info += '<td class="eliminar"><button id="'+carrito[i]['id']+'" type="button" name="EliminarProducto" class="elimnarPro btn btn-danger glyphicon glyphicon-remove"></button></td>';
                info += '<td>'+carrito[i]['nombre']+'</td>';
                info += '<td><input type="number" class="cantidad" min="1" id="cantidad_'+carrito[i]['id']+'" onChange="recarga();" value="'+carrito[i]['cantidad']+'"></td>';
                info += '<td>'+carrito[i]['precio']+'</td>';
            info += '</tr>';
        }
        $('#table_carrito').html(info);
        $('.elimnarPro').click(eliminarCarrito);
        recarga();
    }
    else if(carrito.length==0){
        $('#cancelar').hide();
        $('#comprar').hide();
        $('#table_carrito').hide();
    }
}

function recarga(){
    let cantidad=0, total=0;
    for(var i=0; i< carrito.length; i++){
        carrito[i]['cantidad'] = parseInt($("#cantidad_"+carrito[i]['id']).val()) ;
        cantidad+=carrito[i]['cantidad'];
        total+=carrito[i]['cantidad']*carrito[i]['precio'];
    }
    $('#cantCarrito').html(cantidad);
    $('#total_carrito').html(total);
}

function eliminarCarrito(event){
    for(var i=0; i < carrito.length; i++){
        if(carrito[i].id==event.currentTarget['id']){
            carrito.splice(i,1);
        }
    }
    tableBuy();
    recarga();
}

function limpiarTabla(){
        carrito = [];
        $('#clientSelected').html("");
        recarga();
        tableBuy();
}

function eventPartnerSearch(event){
    clearInterval(intervalo);
    intervalo = setInterval(function(){
        if(event.target.value.trim() != ''){
            NProgress.start();
            $.post( "GetDataShop", { action: event.target.id , info: event.target.value.trim() })
                .done(function( data ) {

                    let cad = '';
                    let list = {};
                    for (var i = 0; i < data.length; i++) {
                        list[ data[i]['id'] ] = data[i];
                        cad += '<div id="'+data[i]['id']+'" class="results '+event.target.id+'">';
                        cad += ''+data[i]['nombre']+' '+data[i]['apellido']+', '+data[i]['email']+', '+data[i]['telefono']+', '+data[i]['celular']+'';
                        cad += '</div>';
                    }

                    if(event.target.id == "inputClients"){
                        $('#numResultadosClientes').html(data.length);
                        $('#resultadosClientes').html(cad);
                        NProgress.done();

                        $('.'+event.target.id).on('click', function(event){
                            $('#clientSelected').html('<span id="eliminarCliente">'+list[ event.target.id ]['nombre']+' '+list[ event.target.id ]['apellido']+', '+list[ event.target.id ]['email']+'<span>');
                            venta['tipo'] = 'cliente';
                            venta['id'] = event.target.id;
                            $('#parnerSelected').html("");
                        });
                    }else{
                        $('#numResultadosSocios').html(data.length);
                        $('#resultadosSocios').html(cad);
                        NProgress.done();

                        $('.'+event.target.id).on('click', function(event){
                            $.post( "GetDataShop", { action: 'ListAutos' , socio: event.target.id })
                                .done(function( dataAutos ) {
                                    let listAutos = '<select class="selectAuto"><option value="-1" name="'+event.target.id+'">-------</option>';
                                    for (var i = 0; i < dataAutos.length; i++) {
                                        listAutos += '<option value="'+dataAutos[i]['id']+'">'+dataAutos[i]['placa']+'</option>';
                                    }
                                    listAutos += '</select>';
                                    $('#parnerSelected').html('<span id="eliminarCliente">'+list[ event.target.id ]['nombre']+' '+list[ event.target.id ]['apellido']+', '+list[ event.target.id ]['email']+'<span> <br> Seleccionar auto: '+listAutos);
                                    venta['tipo'] = 'socio';
                                    venta['id'] = event.target.id;
                                    $('#clientSelected').html("");

                                    $('.selectAuto').change(function(event){
                                        if(event.target.value != '-1'){
                                            venta['tipo'] = 'auto';
                                            venta['id'] = event.target.value;
                                        }else{
                                            venta['tipo'] = 'socio';
                                            venta['id'] = event.target.name;
                                        }
                                    });
                                }).fail(function() {
                                    alert( "error" );
                                });
                        });
                    }
                }).fail(function() {
                    alert( "error" );
                });
        }
        clearInterval(intervalo);
    }, 500);
}

function cleanClientParner(){
    $('#clientSelected').html("");
    $('#parnerSelected').html("");
    venta['tipo'] = null;
    venta['id'] = null;
}

function change(){
    $('.fomatMoney').inputmask("numeric", {
        alias: 'numeric',
        groupSeparator: ',',
        autoGroup: true,
        digits: 2,
        digitsOptional: false,
        prefix: '$ ',
        placeholder: '0',
        autoUnmask: true
    });

    var tableFormatProductos = {
        dom: '<"top"lf>rt<"bottom"Bp>',
        fixedHeader: true,
        buttons: [{
                extend: "csv",
                className: "btn-sm"
            },
            {
                extend: "excel",
                className: "btn-sm"
            }
        ],
        responsive: {
            details: {
                type: 'column'
            }
        },
        lengthMenu: [
            [20, 35, 50, -1],
            [20, 35, 50, "Todo"]
        ],
        order: [
            [1, "asc"]
        ]
    };

    $('#datatable-productos').DataTable(tableFormatProductos);
}

$(window).ready(function(){
    change();
    enviar_ajax("action=actualizarProductos","GetDataBuy",actTablePro);

    $('.btn_cancelarCarrito').click(function(event){
        limpiarTabla();
    });

    $('#formCompraExtra').submit(function(event){
        event.preventDefault();
        if(!existe(event)){
            let price = ( $(event.target[1]).val() != '' )? $(event.target[1]).val() : 0.00;
            carrito=carrito.concat({
                    "id": contExtern,
                    "nombre": $(event.target[0]).val(),
                    "cantidadMax": 1,
                    "cantidad": 1,
                    "precio": price,
                    "cliente": 0,
                    "socio": 0,
                    "descripcion":  $(event.target[2]).val()
                });
            tableBuy();
        }
        event.target.reset();
        contExtern--;
    });

    $('#comprar').on('click',function(event){
        if( carrito.length != 0){
            console.log(carrito);
            enviar_ajax("action=Egresos&carrito="+JSON.stringify(carrito)+"&cliente="+venta['id']+"&tipo="+venta['tipo'], "GetDataBuy", function(event){
                if(event['correcto'] != null){
                    $('#cantDineroCaja').html('$ '+event['caja']);
                }
                enviar_ajax("action=actualizarProductos","GetDataBuy",actTablePro);
                limpiarTabla();
                cleanClientParner();
                document.getElementById("formCompraExtra").reset();
            });
        }
        
        
    });

    for (var i = 0; i < document.getElementsByClassName("buscar").length; i++) {
        document.getElementsByClassName("buscar")[i].addEventListener('keyup', eventPartnerSearch, false);
    }

    $('.limpiar_user_sale').click(cleanClientParner);
});
function enviar_ajax(datos, link, callBack) {
    NProgress.start();
    $.ajax({
        url: link,
        type: 'POST',
        data: datos,
        success: function(text) {
            callBack(text);
            NProgress.done();
        },
        error: function(request, error) {
            console.log([request, error]);
        }
    });
}

function sendForm(form, link, callBack) {
    let sendData = '';
    for (var i = 0; i < form.length; i++) {
        sendData += form[i].name + "=" + $(form[i]).val() + "&";
    };
    enviar_ajax(sendData, link, callBack);
}

function actTableCat(response) {
    $('#datatable-categorias').DataTable().clear().draw();

    let info = [];
    let select = '<select class="form-control" name="categoria">';
    select += '<option value="-1" >-</option>';
    for (let i = 0; i < response.length; i++) {
        info = info.concat({
            0: response[i]['nombre'],
            1: response[i]['descripcion'],
            2: '<button id="' + response[i]['id'] + '" type="button" name="ModificarCategoriaConsulta" class="buttonEventCat btn btn-info glyphicon glyphicon-pencil"></button>' +
                '<button id="' + response[i]['id'] + '" type="button" name="EliminarCategoria" class="buttonEventCat btn btn-danger glyphicon glyphicon-remove"></button>'
        });
        select += '<option value="' + response[i]['id'] + '" >' + response[i]['nombre'] + '</option>';
    }
    select += '</select>';

    $('#datatable-categorias').DataTable().rows.add(info).draw();
    $('#categoriaSelect').html(select);
}

function actTablePro(response) {
    $('#datatable-productos').DataTable().clear().draw();

    let info = [];
    for (var i = 0; i < response.length; i++) {
        info = info.concat({
            0: "",
            1: response[i]['nombre'],
            2: '<button id="' + response[i]['id'] + '" type="button" name="EliminarProducto" class="buttonEventPro btn btn-danger glyphicon glyphicon-remove"></button><br>' +
                '<button id="' + response[i]['id'] + '" type="button" name="ModificarProductoConsulta" class="buttonEventPro btn btn-info glyphicon glyphicon-pencil"></button>',
            3: response[i]['numParte'],
            4: response[i]['ubicacion'],
            5: response[i]['cantidadAlmacen'],
            6: response[i]['descripcion'],
            7: response[i]['idCategoriasProductos'],
            8: "$ " + response[i]['costo'],
            9: "$ " +
                    ( ((parseFloat(response[i]['precioPublico'])+100)/100) * response[i]['costo'] ).toFixed(2) +
                    " - " + response[i]['precioPublico'] + "%",
            10: "$ " + ( ((parseFloat(response[i]['precioSocio'])+100)/100) * response[i]['costo'] ).toFixed(2) + " - " + response[i]['precioSocio'] + "%",
            11: response[i]['unidad'],
            12: response[i]['codigoSat'],
            13: response[i]['claveUnidadSat']
        });
    }

    $('#datatable-productos').DataTable().rows.add(info).draw();
}

function buttonEvent(event) {
    if((event.target.name=="EliminarCategoria" || event.target.name=="EliminarProducto") && !confirm("Eliminar")){
        return false;
    }
    enviar_ajax("action=gestionar&table=" + event.target.name + "&id=" + event.target.id, "GetDataProducts", function(txt) {
        if (event.target.name == "EliminarCategoria") {
            if (txt > 0) {
                $('#datatable-categorias').DataTable().row($(event.target).parent().parent()[0]['_DT_RowIndex']).remove().draw();
                $('#categoriaSelect select option[value=' + event.target.id + ']').remove();
            }
        } else if (event.target.name == "EliminarProducto") {
            if (txt > 0){
                $('#datatable-productos').DataTable().row($(event.target).parent().parent()[0]['_DT_RowIndex']).remove().draw();
            }
        } else if (event.target.name == 'ModificarCategoriaConsulta') {
            $('#categoriasName').html('Modificar categoría');
            $($('#form_categorias').find('#cancelar').siblings('input'))[0].value = "Modificar categoría";
            $('#form_categorias').find('#id')[0].value = txt[0]['id'];
            $('#form_categorias')[0][0].value = txt[0]['nombre'];
            $('#form_categorias')[0][1].value = txt[0]['descripcion'];
        } else if (event.target.name == 'ModificarProductoConsulta') {
            $('#productosName').html('Modificar productos');
            $($('#form_productos').find('#cancelar').siblings('input'))[0].value = "Modificar producto";

            $('#form_productos').find('#id')[0].value = txt[0]['id'];

            $('#form_productos')[0][0].value = txt[0]['nombre'];
            $('#form_productos')[0][1].value = txt[0]['numParte'];
            $('#form_productos')[0][2].value = txt[0]['marca'];
            $('#form_productos')[0][3].value = txt[0]['ubicacion'];
            $('#form_productos')[0][4].value = txt[0]['unidad'];
            $('#form_productos')[0][5].value = txt[0]['cantidadAlmacen'];
            $('#form_productos')[0][6].value = txt[0]['costo'];
            $('#form_productos')[0][7].value = txt[0]['precioPublico'];
            $('#form_productos')[0][8].value = txt[0]['precioSocio'];
            $($('#form_productos')[0][9]).val(txt[0]['idCategoriasProductos']);
            $('#form_productos')[0][10].value = txt[0]['codigoSat'];
            $('#form_productos')[0][11].value = txt[0]['claveUnidadSat'];
            $('#form_productos')[0][12].value = txt[0]['descripcion'];
        }
    });
}

function charge() {
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
    $(".fomatNumber").inputmask("9{1,20}",{
        placeholder: '0',
        digitsOptional: false,
        autoUnmask: true
    });
    $('.fomatPorcentaje').inputmask('9{1,3} %', {
        placeholder: '0',
        digitsOptional: false,
        autoUnmask: true
    });

    jQuery.extend(jQuery.fn.dataTableExt.oSort, {
        "cliente-Socio-asc": function(a, b) {
            var x = $.trim( $.trim(a).split(' - ')[0] ).split('$ ')[1];
            var y = $.trim( $.trim(b).split(' - ')[0] ).split('$ ')[1];
        	x = parseFloat( x );
        	y = parseFloat( y );
        	return ((x < y) ? -1 : ((x > y) ?  1 : 0));
        },

        "cliente-Socio-desc": function(a, b) {
            var x = $.trim( $.trim(a).split(' - ')[0] ).split('$ ')[1];
            var y = $.trim( $.trim(b).split(' - ')[0] ).split('$ ')[1];
        	x = parseFloat( x );
        	y = parseFloat( y );
        	return ((x < y) ?  1 : ((x > y) ? -1 : 0));
        }
    });

    var tableFormatCategorias = {
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
        responsive: true,
        lengthMenu: [
            [20, 35, 50, -1],
            [20, 35, 50, "Todo"]
        ]
    };
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
        columnDefs: [{
            className: 'control',
            orderable: false,
            targets: 0
        }, {
            type: 'cliente-Socio',
            targets: 9
        }, {
            type: 'cliente-Socio',
            targets: 10
        }],
        order: [
            [1, "asc"]
        ]
    };

    $('#datatable-categorias')
        .DataTable(tableFormatCategorias)
        .on('draw', function() {
            $('.buttonEventCat').click(buttonEvent);
        });
    $('#datatable-productos')
        .DataTable(tableFormatProductos)
        .on('draw', function() {
            $('.buttonEventPro').click(buttonEvent);
        });
}

$(window).ready(function() {
    charge();

    enviar_ajax("action=actualizarCategorias", "GetDataProducts", actTableCat);
    enviar_ajax("action=actualizarProductos", "GetDataProducts", actTablePro);

    $('#form_categorias').submit(function(event) {
        event.preventDefault();
        sendForm(event.target, "GetDataProducts", actTableCat);
        $('#categoriasName').html('Alta categoría');
        $($('#form_categorias').find('#cancelar').siblings('input'))[0].value = "Guardar categoría";
        $('#form_categorias').find('#id')[0].value = "-1";
        event.target.reset();
    });
    $('#form_productos').submit(function(event) {
        event.preventDefault();
        sendForm(event.target, "GetDataProducts", actTablePro);
        $('#productosName').html('Alta producto');
        $($('#form_productos').find('#cancelar').siblings('input'))[0].value = "Guardar producto";
        $('#form_productos').find('#id')[0].value = "-1";
        event.target.reset();
    });

    $('.btn_cancelarCategoria').click(function(event) {
        $($(event.target).siblings('input'))[0].value = "Guardar categoría";
        $($(event.target).parent().parent())[0].reset();
    });
    $('.btn_cancelarProducto').click(function(event) {
        $($(event.target).siblings('input'))[0].value = "Guardar producto";
        $($(event.target).parent().parent())[0].reset();
    });

    $( "#precioSocios, #precioPublico, #costo" ).on('input', function(event) {
        $(event.target.nextElementSibling).html(
            "$ "+(((parseFloat(event.target.value)+100)/100) * $('#costo').val() ).toFixed(2)
        );
    });
});

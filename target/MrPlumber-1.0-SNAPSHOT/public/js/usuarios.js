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

        sendData += ($(form[i]).val() != '+' && $(form[i]).val() != '@.')?
                        form[i].name + "=" + $(form[i]).val() + "&" :
                        form[i].name + "=" + "&";
    };
    enviar_ajax(sendData, link, callBack);
}

function actTableCli(response) {
    $('#datatable-clientes').DataTable().clear().draw();
    console.log(response);
    let info = [];
    /*for (let i = 0; i < response.length; i++) {
        info = info.concat({
            0: "",
            1: '<button id="' + response[i]['id'] + '" type="button" name="EliminarCliente" class="buttonEventUse btn btn-danger glyphicon glyphicon-remove"></button><button id="' + response[i]['id'] + '" type="button" name="ModificarClienteConsulta" class="buttonEventCli btn btn-info glyphicon glyphicon-pencil"></button>',
            2: response[i]['nombre'],
            3: response[i]['apellido'],
            4: response[i]['telefono'],
            5: response[i]['celular'],
            6: response[i]['email'],
            7: response[i]['rfc'],
            8: response[i]['estado'],
            9: response[i]['ciudad'],
            10: response[i]['colonia'],
            11: response[i]['calle'],
            12: response[i]['cp'],
            13: response[i]['referencia'],
            14: response[i]['origen']
        });
    }*/

    $('#datatable-clientes').DataTable().rows.add(info).draw();
}

function buttonEvent(event) {
    if(event.target.name == "EliminarCliente" && !confirm("Eliminar")){
        return false;
    }
    enviar_ajax("action=gestionar&table=" + event.target.name + "&id=" + event.target.id, "GetDataClient", function(txt) {
        if (event.target.name == "EliminarCliente") {
            if (txt > 0){
                $('#datatable-clientes').DataTable().row($(event.target).parent().parent()[0]['_DT_RowIndex']).remove().draw();
            }
        } else if (event.target.name == 'ModificarClienteConsulta') {
            $('#clientesName').html('Modificar cliente');
            $($('#form_clientes').find('#cancelar').siblings('input'))[0].value = "Modificar cliente";

            $('#form_clientes').find('#id')[0].value = txt[0]['id'];

            $('#form_clientes')[0][0].value = txt[0]['nombre'];
            $('#form_clientes')[0][1].value = txt[0]['apellido'];
            $('#form_clientes')[0][2].value = txt[0]['telefono'];
            $('#form_clientes')[0][3].value = txt[0]['celular'];
            $('#form_clientes')[0][4].value = txt[0]['email'];
            $('#form_clientes')[0][5].value = txt[0]['rfc'];
            $('#form_clientes')[0][6].value = txt[0]['idEstado'];
            ciudad(txt[0]['idEstado'], txt[0]['idCiudad']);
            $('#form_clientes')[0][8].value = txt[0]['colonia'];
            $('#form_clientes')[0][9].value = txt[0]['calle'];
            $('#form_clientes')[0][10].value = txt[0]['cp'];
            $('#form_clientes')[0][11].value = txt[0]['referencia'];
            $('#form_clientes')[0][12].value = txt[0]['origen'];
        }
    });
}

function charge() {
    $.fn.dataTable.ext.errMode = 'none';
    var tableFormat = {
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

    $('.formatEmail').inputmask("email", {
        placeholder: "@.",
        autoUnmask: true
    });


    $('#datatable-usuarios').DataTable(tableFormat)
        .on('draw', function() {
            $('.buttonEventUse').click(buttonEvent);
        });
}

$(window).ready(function() {
    charge();

    enviar_ajax("action=get", "GetDataUser", actTableCli);

    /*enviar_ajax("action=estados", "GetDataUser", function(response) {
        let select = '<select id="estados" class="form-control" name="estado">';
        select += '<option value="-1" >Selecciona un estado</option>';
        for (let i = 0; i < response.length; i++) {
            select += '<option value="' + response[i]['id'] + '" >' + response[i]['nombre'] + '</option>';
        }
        select += '</select>';
        $('#estadoSelect').html(select);
        $("#estados").change(function(event) {
            ciudad(event.target.value, -1);
        });
    });*/

    /*$('#form_clientes').submit(function(event) {
        event.preventDefault();
        sendForm(event.target, "GetDataClient", actTableCli);
        $('#clientesName').html('Alta clientes');
        $($('#form_clientes').find('#cancelar').siblings('input'))[0].value = "Guardar cliente";
        $('#form_clientes').find('#id')[0].value = "-1";
        event.target.reset();
    });

    $('.btn_cancelarCliente').click(function(event) {
        $($(event.target).siblings('input'))[0].value = "Guardar cliente";
        $($(event.target).parent().parent())[0].reset();
        ciudad(0, -1);
    });*/

});


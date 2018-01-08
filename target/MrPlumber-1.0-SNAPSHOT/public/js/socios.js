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
        sendData += ($(form[i]).val() != '+' && $(form[i]).val() != '@.')?
                        form[i].name + "=" + $(form[i]).val() + "&" :
                        form[i].name + "=" + "&";
    };
    enviar_ajax(sendData, link, callBack);
}

function actTableSoc(response){

    $('#datatable-socios').DataTable().clear().draw();

    let info = [];
    for (let i = 0; i < response.length; i++) {
        info = info.concat(
            {
                0:  '',
                1:  '<button id="'+response[i]['id']+'" type="button" name="EliminarSocio" class="buttonEventSoc btn btn-danger glyphicon glyphicon-remove"></button> <button id="'+response[i]['id']+'" type="button" name="ModificarSocioConsulta" class="buttonEventSoc btn btn-info glyphicon glyphicon-pencil"></button>',
                2:  '<button id="'+response[i]['id']+'" type="button" name="ConsultarSocioAutos" class="buttonEventSoc btn btn-info glyphicon glyphicon-road" data-toggle="modal" data-target="#myModal"></button>',
                3:  response[i]['nombre'],
                4:  response[i]['apellido'],
                5:  response[i]['telefono'],
                6:  response[i]['celular'],
                7:  response[i]['email'],
                8:  response[i]['rfc'],
                9:  response[i]['especialidad'],
                10:  response[i]['idEstado'],
                11:  response[i]['idCiudad'],
                12:  response[i]['calle'],
                13:  response[i]['colonia'],
                14:  response[i]['cp'],
                15:  response[i]['referencia']

            }
        );
    }

    $('#datatable-socios').DataTable().rows.add( info ).draw();
    $('.buttonEventSoc').click(buttonEvent);
}

function actTableEsp(response){

    $('#datatable-especialidad').DataTable().clear().draw();

    let info = [];
    let select = '<select class="form-control" name="especialidad">';
        select += '<option value="-1" >Selecciona una especialidad</option>';
    for (let i = 0; i < response.length; i++) {
        info = info.concat(
            {
                0:  response[i]['nombre'],
                1:  '<button id="'+response[i]['id']+'" type="button" name="ModificarEspecialidadConsulta" class="buttonEventEsp btn btn-info glyphicon glyphicon-pencil"></button> <button id="'+response[i]['id']+'" type="button" name="EliminarEspecialidad" class="buttonEventEsp btn btn-danger glyphicon glyphicon-remove"></button>'
            }
        );
        select += '<option value="'+response[i]['id']+'" >'+response[i]['nombre']+'</option>';
    }
    select += '</select>';

    $('#datatable-especialidad').DataTable().rows.add( info ).draw();
    $('#especialidadSelect').html(select);
    $('.buttonEventEsp').click(buttonEvent);
}

function actTableautos(response){
    $('#datatable-autos').DataTable().clear().draw();
    var info = [];
    if(response.length == 0){
        $('#encargado').html("No hay autos");
        return false;
    }
    $('#idEncargado').attr('name', response[0]['idSocio']);
    $('#encargado span').html(response[0]['nombreSocio']);
    for (var i = 0; i < response.length; i++) {
        info = info.concat(
            {
                0:  '<button id="'+response[i]['id']+'" type="button" name="EliminarAuto" class="buttonEventAuto btn btn-danger glyphicon glyphicon-remove"></button> <button id="'+response[i]['id']+'" type="button" name="formAuto" class="buttonEventAuto btn btn-info glyphicon glyphicon-pencil"></button>',
                1:  response[i]['placa'],
                2:  response[i]['status']
            });
    }
    $('#datatable-autos').DataTable().rows.add( info ).draw();
    $('.buttonEventAuto').click(buttonEvent);
}

function formAuto(id, placa, status, action){
    return [
        '<button id="'+id+'" type="button" name="'+action+'" class="buttonEventAutos btn btn-info glyphicon glyphicon-ok-sign"></button>',
        '<input type="text" id="placaInput" class="form-control formatPlaca" name="placa" placeholder="Placa" value="'+placa+'">',
        '<input type="text" id="statusInput" class="form-control format10" name="status" placeholder="(1=Activo, 0=No Activo)" value="'+status+'">'
    ];
}

function buttonEvent(event){
    if((event.target.name == "EliminarSocio" || event.target.name == "EliminarEspecialidad"|| event.target.name == "EliminarAuto" ) && !confirm("Eliminar")){
        return false;
    }else if(event.target.name == 'ConsultarSocioAutos'){
        $('#datatable-autos').DataTable().clear().draw();
    }

    if(event.target.name == 'GuardarAuto' || event.target.name == 'ModificarAuto'){
        enviar_ajax("action=gestionar&table="+event.target.name+"&idSocio="+event.target.id+"&placa="+$('#placaInput').val()+"&status="+$('#statusInput').val(),"GetDataAssociate",actTableautos);
    }else if(event.target.name == 'formAuto' ){
        $('#datatable-autos').DataTable().row.add( formAuto(
            event.target.id,
            $('#datatable-autos').DataTable().row( $(event.target).parent().parent()[0]['_DT_RowIndex'] ).data()['1'],
            $('#datatable-autos').DataTable().row( $(event.target).parent().parent()[0]['_DT_RowIndex'] ).data()['2'],
            "ModificarAuto"
        ));

        $('#datatable-autos').DataTable().row( $(event.target).parent().parent()[0]['_DT_RowIndex'] ).remove().draw( false );

        $('.formatPlaca').inputmask("AAA-99-99", {
            greedy: false
        });
        $('.format10').inputmask("(1|0)", {
            greedy: false
        });

        $('.buttonEventAutos').click(buttonEvent);
    }else{
        enviar_ajax("action=gestionar&table="+event.target.name+"&id="+event.target.id,"GetDataAssociate",function(txt){
            if(event.target.name == "EliminarEspecialidad"){
                if(txt > 0){
                    $('#datatable-especialidad').DataTable().row( $(event.target).parent().parent()[0]['_DT_RowIndex'] ).remove().draw();
                    $('#especialidadSelect select option[value=' + event.target.id + ']').remove();
                }
            }else if(event.target.name == "EliminarSocio"){
                if(txt > 0)
                    $('#datatable-socios').DataTable().row( $(event.target).parent().parent()[0]['_DT_RowIndex'] ).remove().draw();
            }else if(event.target.name == 'ModificarEspecialidadConsulta'){
                $('#especialidadName').html('Modificar especialidad');
                $($('#form_especialidad').find('#cancelar').siblings('input'))[0].value = "Modificar especialidad";
                $('#form_especialidad').find('#id')[0].value = txt[0]['id'];
                $('#form_especialidad')[0][0].value = txt[0]['nombre'];
            }else if(event.target.name == 'ModificarSocioConsulta'){
                $('#sociosName').html('Modificar socio');
                $($('#form_socios').find('#cancelar').siblings('input'))[0].value = "Modificar socio";
                $('#form_socios').find('#id')[0].value = txt[0]['id'];

                $('#form_socios')[0][0].value = txt[0]['nombre'];
                $('#form_socios')[0][1].value = txt[0]['apellido'];
                $('#form_socios')[0][2].value = txt[0]['telefono'];
                $('#form_socios')[0][3].value = txt[0]['celular'];
                $('#form_socios')[0][4].value = txt[0]['email'];
                $('#form_socios')[0][5].value = txt[0]['rfc'];
                $($('#form_socios')[0][6]).val(txt[0]['especialidad']);
                $('#form_socios')[0][7].value = txt[0]['idEstado'];
                ciudad(txt[0]['idEstado'],txt[0]['idCiudad']);
                $('#form_socios')[0][9].value = txt[0]['colonia'];
                $('#form_socios')[0][10].value = txt[0]['calle'];
                $('#form_socios')[0][11].value = txt[0]['cp'];
                $('#form_socios')[0][12].value = txt[0]['referencia'];
            }else if(event.target.name == 'ConsultarSocioAutos'){
                $('#idEncargado').attr('name', event.target.id);
                actTableautos(txt);
            }else if(event.target.name == 'EliminarAuto'){
                actTableautos(txt);
            }
        });
    }
}

function ciudad(estado,ciudad){
    if(estado!=0){
        enviar_ajax("action=ciudad&ciudad="+estado,"GetDataAssociate",function(responseCity){
            let select = '<select id="ciudad" class="form-control" name="ciudad">';
                select += '<option value="-1" >Selecciona una ciudad</option>';
            for (let i = 0; i < responseCity.length; i++) {
                if(responseCity[i]['id']==ciudad){
                    select += '<option value="'+responseCity[i]['id']+'" selected >'+responseCity[i]['nombre']+'</option>';
                }
                else{
                    select += '<option value="'+responseCity[i]['id']+'" >'+responseCity[i]['nombre']+'</option>';
                }
            }
            select += '</select>';
            $('#ciudadSelect').html(select);
        });
    }
    else{
        $('#ciudad').val(-1);
    }
}

function charge(){
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
        ],
        order: [
            [1, "asc"]
        ]
    };

    $('#datatable-especialidad').DataTable()
        .on('draw', function() {
            $('.buttonEventEsp').click(buttonEvent);
        });
    $('#datatable-socios').DataTable(tableFormat)
        .on('draw', function() {
            $('.buttonEventSoc').click(buttonEvent);
        });
    var tableAutos = $('#datatable-autos').DataTable(tableFormat)
        .on('draw', function() {
            $('.buttonEventAuto').click(buttonEvent);
        });
    var counter = 1;

    $('.addRowAuto').on( 'click', function (event) {

        tableAutos.row.add( formAuto(event.target.name, "", "", "GuardarAuto") ).draw( false );

        $('.formatPlaca').inputmask("AAA-99-99", {
            greedy: false
        });
        $('.format10').inputmask("(1|0)", {
            greedy: false
        });

        $('.buttonEventAutos').click(buttonEvent);
    } );

    $('.formatEmail').inputmask("email", {
        placeholder: "",
        autoUnmask: true
    });
    $('.formatPhone').inputmask("phone", {
        placeholder: "",
        autoUnmask: true
    });
    $('.formatRFC').inputmask({
        regex: "^([A-ZÑa-zñ&]{3,4})[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|1[0-9]|2[0-9]|3[01])([A-Za-z\\d]{2})([A\\d])$",
        autoUnmask: true
    });
    $('.formatPostalCode').inputmask("99999[-9999]", {
        greedy: false,
        autoUnmask: true
    });
}

$(window).ready(function(){
    charge();

    enviar_ajax("action=actualizarEspecialidad","GetDataAssociate",actTableEsp);
    enviar_ajax("action=actualizarSocio","GetDataAssociate",actTableSoc);

    enviar_ajax("action=estados","GetDataAssociate",function(response){
        let select = '<select id="estados" class="form-control" name="estado">';
            select += '<option value="-1" >Selecciona un estado</option>';
        for (let i = 0; i < response.length; i++) {
            select += '<option value="'+response[i]['id']+'" >'+response[i]['nombre']+'</option>';
        }
        select += '</select>';
        $('#estadoSelect').html(select);
        $("#estados").change(function(event) {
            ciudad(event.target.value,-1);
        });
    });

    $('#form_especialidad').submit(function(event){
        event.preventDefault();
        sendForm(event.target, "GetDataAssociate", actTableEsp);
        $('#especialidadName').html('Alta especialidad');
        $($('#form_especialidad').find('#cancelar').siblings('input'))[0].value = "Guardar especialidad";
        $('#form_especialidad').find('#id')[0].value = "-1";
        event.target.reset();
    });

    $('#form_socios').submit(function(event){
        event.preventDefault();
        sendForm(event.target, "GetDataAssociate", actTableSoc);
        $('#sociosName').html('Alta socios');
        $($('#form_socios').find('#cancelar').siblings('input'))[0].value = "Guardar socio";
        $('#form_socios').find('#id')[0].value = "-1";
        event.target.reset();
        ciudad(0,-1);
    });
    $('.btn_cancelarEspecialidad').click(function(event){
        $($(event.target).siblings('input'))[0].value = "Guardar especialidad";
        $($(event.target).parent().parent())[0].reset();
    });

    $('.btn_cancelarSocio').click(function(event){
        $($(event.target).siblings('input'))[0].value = "Guardar socio";
        $($(event.target).parent().parent())[0].reset();
        ciudad(0,-1);
    });

});

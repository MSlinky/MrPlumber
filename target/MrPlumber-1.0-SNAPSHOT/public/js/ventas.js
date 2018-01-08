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

function actTablePro(response) {
    $('#datatable-ventas').DataTable().clear().draw();
    let info = [];
    for (var i = 0; i < response.length; i++) {
        let venta = '';
        if (response[i]['total'] == 0) {
            venta = 'Venta asignada a un auto';
        } else if (response[i]['idCliente'] == null && response[i]['idSocio'] == null) {
            venta = 'Venta realizada sin asignación';
        }else if(response[i]['idCliente'] == null){
            venta = 'Socio';
        }else{
            venta = 'Cliente';
        }
        info = info.concat({
            0: venta,
            1: '<button id="' + response[i]['idFactura'] + '" type="button" class="btn btn-info buttonEventVen" data-toggle="modal" data-target="#myModal" name="infoFactura">Info</button>',
            2: (response[i]['IngresoEgreso'] == '0')? 'Egreso' : 'Ingreso',
            3: response[i]['fecha'],
            4: "$ " + response[i]['total']
        });
    }

    $('#datatable-ventas').DataTable().rows.add(info).draw();
}

function buttonEvent(event){
    enviar_ajax("action=gestionar&table=" + event.target.name + "&id=" + event.target.id, "GetDataSales", function(txt) {
        if(event.target.name == 'infoFactura'){
            $('#datatable-factura').DataTable().clear().draw();
            let info = [];
            let total = 0.0;
            let cant = 0;
            for (var i = 0; i < txt.length; i++) {
                info = info.concat({
                    0: txt[i]['nombre'],
                    1: txt[i]['descripcion'],
                    2: txt[i]['cantidad'],
                    3: txt[i]['precio']
                });
                total += parseFloat(txt[i]['precio']) * parseFloat(txt[i]['cantidad']) ;
                cant += parseFloat(txt[i]['cantidad']) ;
            }
            info = info.concat({
                0: 'Total: ',
                1: '',
                2: cant,
                3: total
            });
            $('#datatable-factura').DataTable().rows.add(info).draw();
        }
    });
}

function change() {
    jQuery.extend(jQuery.fn.dataTableExt.oSort, {
        "date-euro-pre": function(a) {
            var x;

            if ($.trim(a) !== '') {
                // var frDatea = $.trim(a).split(' ');
                // var frTimea = (undefined != frDatea[1]) ? frDatea[1].split(':') : [00,00,00];
                var frDatea2 = a.split(' / ');
                x = (frDatea2[2] + frDatea2[1] + frDatea2[0] /*+ frTimea[0] + frTimea[1] + ((undefined != frTimea[2]) ? frTimea[2] : 0)*/ ) /* *1*/ ;
            } else {
                x = Infinity;
            }

            return x;
        },

        "date-euro-asc": function(a, b) {
            return a - b;
        },

        "date-euro-desc": function(a, b) {
            return b - a;
        }
    });
    var tableFormatVentas = {
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
            [3, "asc"]
        ],
        columnDefs: [{
                "className": "dt-center",
                "targets": "_all"
            },
            {
                targets: 2,
                type: 'date-euro'
            }
        ]
    };
    // $.fn.dataTable.ext.errMode = 'none';
    $('#datatable-ventas').DataTable(tableFormatVentas)
        .on('draw', function() {
            $('.buttonEventVen').click(buttonEvent);
        });
        var tableFormatFactura = {
            dom: '<"top">rt<"bottom"B>',
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
            columnDefs: [
                {
                    "className": "dt-center",
                    "orderable": false,
                    "targets": "_all"
                }
            ],
            order: [
                [3, "asc"]
            ]
        };
    $('#datatable-factura').DataTable(tableFormatFactura);
}

$(window).ready(function() {
    change();
    enviar_ajax("action=salesAll", "GetDataSales", actTablePro);
});



function editEvent(event) {
    $('#event-modal input[name="event-index"]').val(event ? event.id : '');
    $('#event-modal input[name="event-name"]').val(event ? event.name : '');
    $('#event-modal input[name="event-location"]').val(event ? event.location : '');
    $('#event-modal input[name="event-text"]').val(event ? event.text : '');
    $('#event-modal input[name="event-start-date"]').datepicker('update', event ? event.startDate : '');
    $('#event-modal input[name="event-end-date"]').datepicker('update', event ? event.endDate : '');
    $('#event-modal').modal();
}

function deleteEvent(event) {
    var dataSource = $('#calendar').data('calendar').getDataSource();

    for (var i in dataSource) {
        if (dataSource[i].id == event.id) {
            dataSource.splice(i, 1);
            break;
        }
    }

    $('#calendar').data('calendar').setDataSource(dataSource);
}

function saveEvent() {

    var event = {
        id: $('#event-modal input[name="event-index"]').val(),
        name: $('#event-modal input[name="event-name"]').val(),
        location: $('#event-modal input[name="event-location"]').val(),
        text: $('#event-modal input[name="event-text"]').val(),
        startDate: $('#event-modal input[name="event-start-date"]').datepicker('getDate'),
        endDate: $('#event-modal input[name="event-end-date"]').datepicker('getDate')
    }

    var dataSource = $('#calendar').data('calendar').getDataSource();
    
    if (event.id) {
        for (var i in dataSource) {
            if (dataSource[i].id == event.id) {
                dataSource[i].name = event.name;
                dataSource[i].location = event.location;
                dataSource[i].text = event.text;
                dataSource[i].startDate = event.startDate;
                dataSource[i].endDate = event.endDate;
            }
        }
    } else
    {
        var newId = 0;
        for (var i in dataSource) {
            if (dataSource[i].id > newId) {
                newId = dataSource[i].id;
            }
        }

        newId++;
        event.id = newId;

        dataSource.push(event);
        ajaxEnviar(event);
    }

    $('#calendar').data('calendar').setDataSource(dataSource);
    $('#event-modal').modal('hide');
}

function ajaxEnviar(event){
    console.log(event.id);
    console.log(event.name);
    NProgress.start();
    $.post("SendDataEvent",
        {
            id: event.id,
            name: event.name,
            location: event.location,
            text: event.text,
            startDate: event.startDate.toJSON(),
            endDate: event.endDate.toJSON()
        },function (data) {
            NProgress.start();
        }
    );
}
function calendar(datas) {
    $('#calendar').calendar({

        enableContextMenu: true,
        enableRangeSelection: true,
        contextMenuItems: [
            {
                text: 'Update',
                click: editEvent
            },
            {
                text: 'Delete',
                click: deleteEvent
            }
        ],
        selectRange: function (e) {
            editEvent({startDate: e.startDate, endDate: e.endDate});
        },
        mouseOnDay: function (e) {
            console.log("que pex loco");
            if (e.events.length > 0) {
                var content = '';

                for (var i in e.events) {
                    console.log(":::");
                    content += '<div class="event-tooltip-content">'
                            + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                            + '<div class="event-location">' + e.events[i].location + '</div>'
                            + '<div class="event-text">' + e.events[i].text + '</div>'
                            + '</div>';
                }

                $(e.element).popover({
                    trigger: 'manual',
                    container: 'body',
                    html: true,
                    content: content
                });

                $(e.element).popover('show');
            }
        },
        mouseOutDay: function (e) {
            if (e.events.length > 0) {
                $(e.element).popover('hide');
            }
        },
        dayContextMenu: function (e) {
            $(e.element).popover('hide');
        },
        dataSource: datas
    });
    console.log(datas);
}

function ajax() {
    $.ajax({
        url: "GetDataEvent",
        type: 'POST',
        success: function (data) {
            
        var datas = [];    
        for (var i = 0; i < data.data.length; i++){
            var date = data.data[i].startdate;
            var yearS = date.substring(0, 4);
            var monthS = date.substring(5, 7);
            var dayS = date.substring(8, 10);
            var date = data.data[i].enddate;
            var yearM = date.substring(0, 4);
            var monthM = date.substring(5, 7);
            var dayM = date.substring(8, 10);
            console.log(yearS+" "+monthS+" "+dayS);
            datas.push({ 
                id: data.data[i].idEventos,
                name: data.data[i].name,
                location: data.data[i].location,
                text: data.data[i].text,
                startDate: new Date(parseInt(yearS), parseInt(monthS)-1, parseInt(dayS)),
                endDate: new Date(parseInt(yearM), parseInt(monthM)-1, parseInt(dayM))
            });
            //if (i + 1 < data.data.length)datas += ",";
            
        } 
        calendar(datas);
        },
        error: function (request, error) {
            console.log([request, error]);
        }
    });
}

$(function () {
    console.log("entro");

    ajax();
    var currentYear = new Date().getFullYear();


    $('#save-event').click(function () {
        saveEvent();
    });
});
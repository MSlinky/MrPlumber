<!DOCTYPE html>
<%

    HttpSession misession = (HttpSession) request.getSession();

    try {
        if (misession.getAttribute("sesion").equals(false)) {
            response.sendRedirect("Login");
        }
    } catch (NullPointerException e) {
        System.out.println(e);
        response.sendRedirect("Login");
    }


%>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Agenda | </title>

        <!-- Bootstrap -->
        <link href="plantilla/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="plantilla/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <!-- NProgress -->
        <link href="plantilla/vendors/nprogress/nprogress.css" rel="stylesheet">
        <!-- Datatables -->
        <link href="plantilla/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="plantilla/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
        <link href="plantilla/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
        <link href="plantilla/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
        <link href="plantilla/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="plantilla/calendar/css/bootstrap-year-calendar.min.css">

        <!-- Custom Theme Style -->
        <link href="plantilla/build/css/custom.min.css" rel="stylesheet">
        <link href="public/css/personal_custom.css" rel="stylesheet">
    </head>

    <body class="nav-sm">
        <div class="container body">
            <div class="main_container">

                <%@include  file="./static/00header.jsp" %>

                <!-- page content -->
                <div class="right_col" role="main">


                    <div class="sub-content-container">

                        <div class="sub-content"><div class="panel panel-default" style="margin:10px;">
                                <div class="panel-heading"></div>
                                <div class="panel-body">
                                    <div id="calendar" class="calendar">
                                    </div>   
                                </div>
                            </div>
                            <div class="modal modal-fade" id="event-modal">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                            <h4 class="modal-title">
                                                Event
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" name="event-index">
                                            <form class="form-horizontal">
                                                <div class="form-group">
                                                    <label for="min-date" class="col-sm-4 control-label">Name</label>
                                                    <div class="col-sm-7">
                                                        <input name="event-name" type="text" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="min-date" class="col-sm-4 control-label">Location</label>
                                                    <div class="col-sm-7">
                                                        <input name="event-location" type="text" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="min-date" class="col-sm-4 control-label">Description</label>
                                                    <div class="col-sm-7">
                                                        <input name="event-text" type="text" class="form-control">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="min-date" class="col-sm-4 control-label">Dates</label>
                                                    <div class="col-sm-7">
                                                        <div class="input-group input-daterange" data-provide="datepicker">
                                                            <input name="event-start-date" type="text" class="form-control" value="2012-04-05">
                                                            <span class="input-group-addon">to</span>
                                                            <input name="event-end-date" type="text" class="form-control" value="2012-04-19">
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                            <button type="button" class="btn btn-primary" id="save-event">
                                                Save
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="context-menu">
                            </div>
                        </div>
                        <!-- footer content -->



                        <footer>
                            <div>
                                Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
                            </div>
                        </footer>
                        <!-- /footer content -->
                    </div>

                </div>
                <!-- /page content -->
            </div>

            <!-- Bootstrap -->
            <script src="plantilla/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
            <!-- NProgress -->
            <script src="plantilla/vendors/nprogress/nprogress.js"></script>
            <!-- bootstrap-progressbar -->
            <script src="plantilla/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
            <!-- iCheck -->
            <script src="plantilla/vendors/iCheck/icheck.min.js"></script>
            <!-- InputMask -->
            <script src="plantilla/vendors/Inputmask-4.x/dist/jquery.inputmask.bundle.js"></script>
            <!-- Datatables -->
            <script src="plantilla/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
            <script src="plantilla/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
            <script src="plantilla/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
            <script src="plantilla/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
            <script src="plantilla/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
            <script src="plantilla/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
            <script src="plantilla/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
            <script src="plantilla/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
            <script src="plantilla/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
            <script src="plantilla/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
            <script src="plantilla/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
            <script src="plantilla/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
            <script src="plantilla/vendors/jszip/dist/jszip.min.js"></script>
            <script src="plantilla/vendors/pdfmake/build/pdfmake.min.js"></script>
            <script src="plantilla/vendors/pdfmake/build/vfs_fonts.js"></script>
            <!-- Custom Theme Scripts -->
            <script src="plantilla/build/js/custom.js"></script>
            <script src="public/js/personal_custom.js"></script>
            <!--Calendar-->
            <script src="plantilla/calendar/js/bootstrap-year-calendar.js"></script>
            <script src="plantilla/calendar/js/bootstrap-year-calendar.min.js"></script>
            <script src="plantilla/calendar/js/bootstrap-datepicker.min.js"></script>
            <script src="public/js/agenda.js"></script>

    </body>
</html>

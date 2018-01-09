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

        <title>Usuarios | </title>

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

                    <div class="col-12 col-md-6">
                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2 id="clientesName">Alta usuarios</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <form  id="form_alta_clientes">
                                    <div class="col-12 col-md-6">
                                        <input type="email" class="form-control formatEmail" placeholder="Correo"/>
                                    </div>
                                    <div class="col-12 col-md-6">
                                        <input type="password" class="form-control" placeholder="Contraseña"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 col-md-6">
                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2>Lista de Usuarios</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table id="datatable-usuarios" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th class="all" style="width: 10px;"></th>
                                            <th style="width: 100px;">Configuración</th>
                                            <th style="width: 100px;">Correo</th>
                                            <th style="width: 100px;">Tipo Usuario</th>
                                        </tr>
                                    </thead>
                                    <tbody id="table_usuarios">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /page content -->

                <!-- footer content -->
                <footer>
                    <div>
                        Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
                    </div>
                </footer>
                <!-- /footer content -->
            </div>
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
        <script src="plantilla/vendors/Inputmask-4.x/dist/inputmask/phone-codes/phone.js"></script>
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
        <!-- Script productos -->
        <script src="public/js/usuarios.js"></script>

    </body>
</html>
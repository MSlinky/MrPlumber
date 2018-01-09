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

        <title>Clientes | </title>

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
                    <div class="modal fade" id="myModalBalance" role="dialog">
                        <div class="modal-dialog">

                        <!-- Modal content-->
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title" id="encargado">Balance</h4>
                          </div>
                          <div class="modal-body">
                              <table id="datatable-balance" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                                  <thead>
                                    <tr>
                                      <th style="width: 10px;">ID</th>
                                      <th>Dinero</th>
                                      <th>Fecha</th>
                                      <th>Descripción</th>
                                    </tr>
                                </thead>
                                <tbody id="table_Balance">
                                    </tbody>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                                </div>
                            </div>
                            </div>
                        </div>
                    
                    <div class="col-md-12 col-xs-12">
                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2 id="clientesName">Alta clientes</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <form id="form_clientes" class="form-horizontal form-label-left">
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Nombre<span class="required">*</span></label>
                                        <input type="text" class="form-control" name="nombre" required="required" placeholder="Nombre">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Apellido<span class="required">*</span></label>
                                        <input type="text" class="form-control" name="apellido" placeholder="Apellido" required>
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Teléfono</label>
                                        <input type="text" class="form-control formatPhone" name="telefono" placeholder="Telefono">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Celular</label>
                                        <input type="text" class="form-control formatPhone" data-inputmask="'alias': 'phone'" name="celular" placeholder="Celular">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Email</label>
                                        <input type="text" class="form-control formatEmail" name="correo" placeholder="Correo">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">RFC</label>
                                        <input type="text" class="form-control formatRFC" style="text-transform:uppercase;" name="rfc" placeholder="RFC">
                                    </div>
                                    <div class="ln_solid form-group col-md-12 col-sm-12 col-xs-12"></div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Estado<span class="required">*</span></label>
                                        <div id="estadoSelect">
                                            <select class="form-control" name="estado">
                                                <option value="-1" >Selecciona un estado</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Ciudad<span class="required">*</span></label>
                                        <div id="ciudadSelect">
                                            <select class="form-control" name="ciudad">
                                                <option value="-1" >Selecciona una ciudad</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Colonia</label>
                                        <input type="text" class="form-control" name="colonia" placeholder="Colonia">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">Calle</label>
                                        <input type="text" class="form-control" name="calle" placeholder="Calle">
                                    </div>
                                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                                        <label class="control-label" for="first-name">CP</label>
                                        <input type="text" class="form-control formatPostalCode" name="cp" placeholder="cp">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-12 col-sm-12 col-xs-12" for="last-name">Referencia</label>
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <textarea class="form-control"  name="referencia" ></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-12 col-sm-12 col-xs-12" for="last-name">Origen</label>
                                        <div class="col-md-12 col-sm-12 col-xs-12">
                                            <select name="origen">
                                                <option value="Facebook">Facebook</option>
                                                <option value="Espectacular">Espectacular</option>
                                                <option value="Conocido">Conocido</option>
                                                <option value="otro">Otro</option>
                                            </select>   
                                        </div>
                                    </div>
                                    <input style="display:none;" type="text" class="form-control" id="id" name="id">
                                    <div class="ln_solid"></div>
                                    <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                                        <input type="submit" class="btn btn-success" name="action" value="Guardar cliente">
                                        <div id="cancelar" class="btn_cancelarCliente btn btn-primary">Cancelar</div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 col-xs-12">
                        <div class="x_panel tile">
                            <div class="x_title">
                                <h2>Lista de Clientes</h2>
                                <ul class="nav navbar-right panel_toolbox">
                                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                            <div class="x_content">
                                <table id="datatable-clientes" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th class="all"></th>
                                            <th style="width: 10px;">Configuración</th>
                                            <th>Nombre</th>
                                            <th>Apellido</th>
                                            <th>Balance</th>
                                            <th>Cuenta</th>
                                            <th>Teléfono</th>
                                            <th>Celular</th>
                                            <th>Email</th>
                                            <th>RFC</th>
                                            <th>Estado</th>
                                            <th>Ciudad</th>
                                            <th class="none">Colonia</th>
                                            <th class="none">Calle</th>
                                            <th class="none">CP</th>
                                            <th class="none">Referencia</th>
                                            <th class="none">Origen</th>
                                        </tr>
                                    </thead>
                                    <tbody id="table_clientes">

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
        <script src="public/js/clientes.js"></script>

    </body>
</html>

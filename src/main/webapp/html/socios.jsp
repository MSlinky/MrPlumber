<!DOCTYPE html>
<%

        HttpSession misession = (HttpSession) request.getSession();
        
        try{
            if(misession.getAttribute("sesion").equals(false)){
               response.sendRedirect("Login");
            }
        }catch(NullPointerException e){
            System.out.println(e);
            response.sendRedirect("Login");
        }
    

    %>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Socios | </title>

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

            <!-- Modal -->
            <div class="modal fade" id="myModal" role="dialog">
              <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="encargado">Autos de <span></span></h4>
                    <button id="idEncargado" class="addRowAuto">Agregar auto</button>
                  </div>
                  <div class="modal-body">
                      <table id="datatable-autos" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                          <thead>
                            <tr>
                              <th style="width: 10px;">Configuración</th>
                              <th>Placas</th>
                              <th>Estatus</th>
                            </tr>
                          </thead>
                          <tbody id="table_autos">

                          </tbody>
                      </table>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                  </div>
                </div>

              </div>
            </div>

          <div class="col-md-4 col-sm-4 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2 id="especialidadName">Alta Especialidad</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="height: 150px;">
                  <form id="form_especialidad" class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-12 col-sm-12 col-xs-12" for="first-name">Nombre de especialidad<span class="required">*</span></label>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                          <input type="text" class="form-control" name="nombre" required="required" >
                        </div>
                      </div>
                        <input style="display:none;" type="text" class="form-control" id="id" name="id">
                        <div class="ln_solid"></div>
                        <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                            <input type="submit" class="btn btn-success" name="action" value="Guardar especialidad">
                            <div id="cancelar" class="btn_cancelarEspecialidad btn btn-primary">Cancelar</div>
                        </div>
                    </form>
              </div>
            </div>
          </div>

          <div class="col-md-8 col-sm-8 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Lista de Especialidades</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                <table id="datatable-especialidad" class="display table table-striped table-bordered" cellspacing="0">
                    <thead>
                      <tr>
                        <th>Nombre</th>
                        <th style="width: 10px;">Configuración</th>
                      </tr>
                    </thead>
                    <tbody id="table_especialidad">

                    </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="col-md-12 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2 id="clientesName">Alta Socios</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                  <form id="form_socios" class="form-horizontal form-label-left">
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Nombre<span class="required">*</span></label>
                        <input type="text" class="form-control" name="nombre" required="required" placeholder="Nombre">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Apellido<span class="required">*</span></label>
                        <input type="text" class="form-control" name="apellido" placeholder="Apellido">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Telefono</label>
                        <input type="text" class="form-control formatPhone" name="telefono" placeholder="Telefono" >
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Celular</label>
                        <input type="text" class="form-control formatPhone" name="celular" placeholder="Celular">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Email</label>
                        <input type="text" class="form-control formatEmail" name="correo" placeholder="correo">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">RFC</label>
                        <input type="text" class="form-control formatRFC" name="rfc" placeholder="RFC">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Especialidad<span class="required">*</span></label>
                        <div id="especialidadSelect"></div>
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
                        <input style="display:none;" type="text" class="form-control" id="id" name="id">
                        <div class="ln_solid"></div>
                        <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                            <input type="submit" class="btn btn-success" name="action" value="Guardar socio">
                            <div id="cancelar" class="btn_cancelarSocio btn btn-primary">Cancelar</div>
                        </div>
                  </form>
              </div>
            </div>
          </div>

          <div class="col-md-12 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Lista de Socios</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                <table id="datatable-socios" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                    <thead>
                      <tr>
                        <th class="all"></th>
                        <th>Configuración</th>
                        <th>Autos</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Telefono</th>
                        <th>Celular</th>
                        <th>Email</th>
                        <th>RFC</th>
                        <th>Especialidad</th>
                        <th>Estado</th>
                        <th>Ciudad</th>
                        <th class="none">Calle</th>
                        <th class="none">Colonia</th>
                        <th class="none">CP</th>
                        <th class="none">Referencia</th>
                      </tr>
                    </thead>
                    <tbody id="table_socios">

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
    <script src="public/js/socios.js"></script>

  </body>
</html>

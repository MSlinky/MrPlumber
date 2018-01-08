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

    <title>Productos | </title>

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

          <div class="col-md-4 col-sm-4 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2 id="categoriasName">Alta categoría</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="height: 250px;">
                  <form id="form_categorias" class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-12 col-sm-12 col-xs-12" for="first-name">Nombre de categoría<span class="required">*</span></label>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                          <input type="text" class="form-control" name="nombre" required="required" >
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Descripcion</label>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                          <textarea class="form-control"  name="descripcion" ></textarea>
                        </div>
                      </div>
                        <input style="display:none;" type="text" class="form-control" id="id" name="id">
                        <div class="ln_solid"></div>
                        <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                            <input type="submit" class="btn btn-success" name="action" value="Guardar categoría">
                            <div id="cancelar" class="btn_cancelarCategoria btn btn-primary">Cancelar</div>
                        </div>
                    </form>
              </div>
            </div>
          </div>

          <div class="col-md-8 col-sm-8 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Lista de categorías</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                <table id="datatable-categorias" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                    <thead>
                      <tr>
                        <th>Nombre</th>
                        <th>Descripci&#243;n</th>
                        <th style="width: 50px;">Configurar</th>
                      </tr>
                    </thead>
                    <tbody id="table_categorias">

                    </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="col-md-12 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Alta productos</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content">
                <form id="form_productos" class="form-horizontal form-label-left">
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Nombre<span class="required">*</span></label>
                        <input type="text" class="form-control" name="nombre" required="required" placeholder="Nombre">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">N&#250;mero de parte</label>
                        <input type="text" class="form-control" name="numeroParte" placeholder="N&#250;mero de parte">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Marca</label>
                        <input type="text" class="form-control" name="marca" placeholder="Marca">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Ubicaci&#243;n</label>
                        <input type="text" class="form-control" name="ubicacion" placeholder="0-9, A-Z">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Unidad</label>
                        <input type="text" class="form-control" name="unidad" placeholder="Inividual / Paquete / Kit">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Cantidad en el almac&eacute;n<span class="required">*</span></label>
                        <input type="text" class="fomatNumber form-control" name="cantidadAlmacen" placeholder="Cantidad almac&eacute;n" >
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Costo<span class="required">*</span></label>
                        <input id="costo" type="text" class="fomatMoney form-control" style="text-align: right;" name="costo" value="$ 0.00">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Precio publico<span class="required">*</span></label>
                        <input id="precioPublico" type="text" class="fomatPorcentaje form-control" style="text-align: right;" name="precioPublico">
                        <div class="formMessage"></div>
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Precio socio<span class="required">*</span></label>
                        <input id="precioSocios" type="text" class="fomatPorcentaje form-control" style="text-align: right;" name="precioSocio">
                        <div class="formMessage"></div>
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Categorias<span class="required">*</span></label>
                        <div id="categoriaSelect"></div>
                    </div>
                    <div class="ln_solid form-group col-md-12 col-sm-12 col-xs-12"></div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">C&#243;digo SAT</label>
                        <input type="text" class="form-control" name="codigoSAT" placeholder="C&#243;digo SAT">
                    </div>
                    <div class="form-group col-md-4 col-sm-8 col-xs-12">
                        <label class="control-label" for="first-name">Clave Unidad SAT</label>
                        <input type="text" class="form-control" name="claveUnidadSAT" placeholder="Clave Unidad SAT">
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-12 col-sm-12 col-xs-12" for="last-name">Descripci&#243;n</label>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <textarea class="form-control"  name="descripcion" ></textarea>
                        </div>
                    </div>
                    <input style="display:none;" type="text" class="form-control" id="id" name="id">
                    <div class="ln_solid"></div>
                    <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                        <input type="submit" class="btn btn-success" name="action" value="Guardar producto">
                        <div id="cancelar" class="btn_cancelarCategoria btn btn-primary">Cancelar</div>
                    </div>
                </form>
              </div>
            </div>
          </div>

          <div class="col-md-12 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Lista de Productos</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="overflow: auto;">
                  <table id="datatable-productos" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                    <thead>
                      <tr>
                        <th class="all"></th>
                        <th style="width: 100px;">Nombre</th>
                        <th>Conf.</th>
                        <th style="width: 10px;">Num. de parte</th>
                        <th style="width: 10px;">Sitio</th>
                        <th style="width: 10px;">Cant. Almac.</th>
                        <th style="width: 175px;">Descripci&#243;n</th>
                        <th style="width: 175px;">Categorias</th>
                        <th  style="width: 10px;">Costo</th>
                        <th>Precio publico</th>
                        <th>Precio socio</th>
                        <th class="none">Unidad</th>
                        <th class="none">Codigo SAT</th>
                        <th class="none">Clave unidad SAT</th>
                      </tr>
                    </thead>
                    <tbody id="table_productos">

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
    <script src="public/js/productos.js"></script>

  </body>
</html>

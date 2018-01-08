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

    <title>Carrito | </title>

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
          <div class="col-md-8 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2 id="carritoName">Carrito</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="height: 300px; overflow:auto;">
                  <form id="form_carrito" class="form-horizontal form-label-left">

                        <input style="display:none;" type="text" class="form-control" id="id" name="id">
                        <div class="ln_solid"></div>
                        <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                            <table id="datatable-carrito" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                                <thead>
                                  <tr>
                                    <th>Eliminar</th>
                                    <th>Nombre</th>
                                    <th>Cantidad</th>
                                    <th>Precio</th>
                                  </tr>
                                </thead>
                                <tbody id="table_carrito">

                                </tbody>
                                <tfoot >
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td id="cantCarrito"></td>
                                        <td id="total_carrito"></td>
                                    </tr>
                                </tfoot>
                            </table>
                            <div id="comprar" class="btn_comprarCarrito btn btn-primary">Comprar</div>
                            <div id="cancelar" class="btn_cancelarCarrito btn btn-primary">Cancelar</div>
                        </div>
                    </form>
              </div>
            </div>
          </div>

          <div class="col-md-4 col-xs-12 ">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2 id="carritoName">Agregar</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-down"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="overflow: visible">
                  <div class="">
                      <form id="formCompraExtra">
                          <div class="form-group" >
                            <input type="text" class="form-control" placeholder="Nombre del gasto*" required>
                          </div>
                          <div class="form-group" >
                             <input id="costo" type="text" class="fomatMoney form-control" style="text-align: right;" name="costo" value="$ 0.00">
                          </div>
                          <div class="form-group">
                            <textarea class="form-control" rows="3" placeholder="Descripción"></textarea>
                          </div>
                          <div class="ln_solid"></div>
                          <div class="submit form-group col-md-12 col-sm-12 col-xs-12">
                              <input type="submit" class="btn btn-success" name="action" value="Agregar compra">
                              <div id="cancelar" class="btn_cancelarCliente btn btn-primary">Cancelar</div>
                          </div>
                      </form>
                  </div>
              </div>
            </div>
          </div>

          <div class="col-md-12 col-xs-12">
            <div class="x_panel tile">
              <div class="x_title">
                  <h2>Productos</h2>
                  <ul class="nav navbar-right panel_toolbox">
                    <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
                  </ul>
                <div class="clearfix"></div>
              </div>
              <div class="x_content" style="overflow: auto;">
                  <table id="datatable-productos" class="display table table-striped table-bordered" cellspacing="0" width="100%">
                    <thead>
                      <tr>
                        <th>Agregar</th>
                        <th>Nombre</th>
                        <th>Numero de parte</th>
                        <th>Ubicaci&#243;n</th>
                        <th>Unidad</th>
                        <th>Descripci&#243;n</th>
                        <th>Categorias</th>
                        <th>Costo</th>
                        <th>Precio publico</th>
                        <th>Precio socio</th>
                        <th>Cantidad Almacen</th>
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
    <!-- InputMask -->
    <script src="plantilla/vendors/Inputmask-4.x/dist/jquery.inputmask.bundle.js"></script>
    <!-- NProgress -->
    <script src="plantilla/vendors/nprogress/nprogress.js"></script>
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
    <script src="public/js/compras.js"></script>

  </body>
</html>

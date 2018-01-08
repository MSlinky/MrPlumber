<%@page import="pojo.Usuario"%>
<script src="plantilla/vendors/jquery/dist/jquery.min.js"></script>
<script src="public/js/header.js"></script>
<script>
    var valor=localStorage['caja'];
    var cantidad;
    var ventana;
    if(valor==undefined){
    }
</script>
<div class="col-md-3 left_col">
  <div class="left_col scroll-view">
    <div class="navbar nav_title" style="border: 0;">
      <a href="Inicio" class="site_title"></a>
    </div>

    <div class="clearfix"></div>

    <!-- menu profile quick info -->
    <div class="profile clearfix">
      <div class="profile_pic">
        <img src="public/img/user.svg" alt="..." class="img-circle profile_img">
      </div>
      <div class="profile_info">
        <span>Welcome,</span>
        <h2>MrPlumber</h2>
      </div>
    </div>
    <!-- /menu profile quick info -->

    <br />

    <!-- sidebar menu -->
    <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
      <div class="menu_section">
        <h3>General</h3>
        <ul class="nav side-menu">
          <li><a href="Inicio"><i class="fa fa-home"></i> Inicio <span class="fa"></span></a>
          </li>
          <li><a><i class="fa fa-desktop"></i> Productos <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu">
              <li><a href="Productos">Gestor de productos</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-shopping-cart"></i> Ingesos y Egresos <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu">
              <li><a href="Carrito">Carrito</a></li>
              <li><a href="Compras">Compras</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-hand-o-right"></i> Clientes <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu">
              <li><a href="Clientes">Clientes</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-child"></i> Usuarios <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu"> 
              <li><a href="Usuarios">Usuarios</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-hand-peace-o"></i> Socios <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu">
              <li><a href="Socios">Socios</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-compress"></i> Ventas <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu">
              <li><a href="Ventas">Ventas</a></li>
            </ul>
          </li>
          <li><a><i class="fa fa-calendar-o"></i> Agenda <span class="fa fa-chevron-down"></span></a>
            <ul class="nav child_menu"> 
              <li><a href="Agenda">Agenda</a></li>
            </ul>
          </li>
        </ul>
      </div>

    </div>
    <!-- /sidebar menu -->

    <!-- /menu footer buttons -->
    <div class="sidebar-footer hidden-small">
      <a data-toggle="tooltip" data-placement="top" title="Logout" href="login.html">
        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
      </a>
    </div>
    <!-- /menu footer buttons -->
  </div>
</div>

<!-- top navigation -->
<div class="top_nav">
  <div class="nav_menu">
    <nav>
      <div class="nav toggle">
        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
      </div>

        <div class="alert alert-danger alert-dismissable fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <div id="alertMessage" ></div>
        </div>

      <ul class="nav navbar-nav navbar-right">
        <li class="">
          <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
            <img src="public/img/user.svg" alt="">Mr Plumber
            <span class=" fa fa-angle-down"></span>
          </a>
          <ul class="dropdown-menu dropdown-usermenu pull-right">
            <!-- <li><a >Perfil</a></li> -->
            <li>
              <a href="">
                <span>Dinero en caja: <span id="cantDineroCaja">
                    <%@ page import="model.Conexion" %>
                    <%
                        Conexion objConexion = new Conexion();
                        objConexion.prepareStatement("SELECT dineroCaja FROM datosemisor WHERE id=1");
                        objConexion.executeQuery();
                        double dinero = Double.parseDouble(objConexion.QueryToList().get(0).get("dineroCaja"));
                        out.write("$ "+dinero);
                        objConexion.Close();
                    %></span></span>
              </a>
            </li>
            <!-- <li>
              <a href="">
                <span>Configuración</span>
              </a>
            </li> -->
            <li><a href="signin"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
          </ul>
        </li>

        <li role="presentation" class="dropdown">
          <a class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
            <i class="fa fa-envelope-o"></i>
            <span  id="NoAlertas" class="badge bg-green"></span>
          </a>
          <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
            <div id="agenda"></div>
            <li>
              <div class="text-center">
                  <a href="Agenda">
                  <strong>See All Alerts</strong>
                  <i class="fa fa-angle-right"></i>
                </a>
              </div>
            </li>
          </ul>
        </li>
      </ul>
    </nav>
  </div>
</div>

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

    <title>Inicio | </title>

    <!-- Bootstrap -->
    <link href="plantilla/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="plantilla/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="plantilla/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="plantilla/build/css/custom.min.css" rel="stylesheet">
    <link href="public/css/personal_custom.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">

        <%@include  file="./static/00header.jsp" %>

        <!-- page content -->
        <div class="right_col" role="main" style="min-height: 1704px;">

        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- Bootstrap -->
    <script src="plantilla/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="plantilla/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="plantilla/vendors/iCheck/icheck.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="plantilla/build/js/custom.min.js"></script>

  </body>
</html>

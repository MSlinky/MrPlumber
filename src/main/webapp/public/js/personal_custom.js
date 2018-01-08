$.fn.dataTable.ext.errMode = 'none';
window.addEventListener('load', calculos, false);
function calculos() {
  $('#sidebar-menu').find('li.active').addClass('active-sm').removeClass('active');
  $('.child_menu').css({
    'display':'none'
  });
}

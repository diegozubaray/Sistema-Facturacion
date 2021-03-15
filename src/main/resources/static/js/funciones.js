function eliminar(id){

const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
  
    confirmButton: 'btn btn-success mx-3',
    cancelButton: 'btn btn-danger ',

  },
  buttonsStyling: false
})

swalWithBootstrapButtons.fire({
  title: 'Estas seguro de eliminar?',
  text: "No se podra recuperar!",
  icon: 'warning',
  showCancelButton: true,
  confirmButtonText: 'Si, Borrar!',
  cancelButtonText: 'No, cancelar!',
  reverseButtons: true,

}).then((result) => {
  if (result.isConfirmed) {
  $.ajax({
  url:"/eliminar/" + id,
  success: function(res){
  console.log(res);
  location.href="/listar";
  }
  });
    swalWithBootstrapButtons.fire(
      'Borrado!',
      'Se elimino correctamente.',   
      'success'
      
    )
  } else if (
    /* Read more about handling dismissals below */
    result.dismiss === Swal.DismissReason.cancel
  ) {
    swalWithBootstrapButtons.fire(
      'Cancelado',
      'El cliente No se ha eliminado',
      'error'
    )
  }
})

}
$(document).ready(function cart(productId)
            {
            $.ajax({
                type:'get',
                url:'cart.html',
                data:{
                  id:productId
                },
                success:function(response) {
                  document.getElementById("total_items").textContent=response;
                }
              });
            }
);
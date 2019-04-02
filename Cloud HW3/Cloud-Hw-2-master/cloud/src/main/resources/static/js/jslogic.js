  $(document).ready(function() {
        
         $('#t1').hide();
         $('#t2').hide();
         $("#b").click(function(){
                $('#ripe tbody').empty();
                $('#luke tbody').empty();
                var date  = $("#date").val();
                $.ajax({
                      url: '/forecast/'+date,
                      method: "GET",
                      success: function (response) {
                          var tip = '';
                          for(i in response){
                        	  
   tip += '<tr><td>' + response[i].date + '</td><td>' + response[i].tmin + '</td><td>' + response[i].tmax + '</td></tr>';
                              
                            }                            
                          $('#ripe').append(tip);
                          $('#luke').append(tip);
                          $('#t1').show();
                              $('#t2').show();
                      }
                  });
              });
  });
  

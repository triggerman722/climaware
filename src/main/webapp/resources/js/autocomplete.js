function initac() {
var input = document.getElementById('pac-location');
if (input!=null) {
google.maps.event.addDomListener(input, 'keydown', function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
  });
var autocomplete = new google.maps.places.Autocomplete(input);
google.maps.event.addListener(autocomplete, 'place_changed', function () {
            var place = autocomplete.getPlace();
            document.getElementById('hid-latitude').value = place.geometry.location.lat();
            document.getElementById('hid-longitude').value = place.geometry.location.lng();

        });
}
}

if(window.addEventListener){
  window.addEventListener('load', initac)
}else{
  window.attachEvent('onload', initac)
}

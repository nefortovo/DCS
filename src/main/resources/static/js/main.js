"use ctrict"

function check() {
    var submit = document.getElementById('submit');
    if (document.getElementById('politics').checked){
        submit.disabled = '';
    }
    else
    submit.disabled = 'disabled';
}

var phoneMask = IMask(document.getElementById('phone'),
{
    mask: '+{7}(000)000-00-00'
});

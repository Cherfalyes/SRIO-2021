
 function androidShowToast(msg){
   Android.showToast(msg);
 }
 function androidShowID(){
    Android.showID();
 }

 function getContact(){
    const contactsString = Android.getContacts();
    let contacts =JSON.parse(contactsString);
    document.body.appendChild(buildHtmlTable(contacts));
 }

 var _table_ = document.createElement('table'),
    _tr_ = document.createElement('tr'),
    _th_ = document.createElement('th'),
    _td_ = document.createElement('td');


        


 function buildHtmlTable(arr) {
     var table = _table_.cloneNode(false),rIndex,
         columns = addAllColumnHeaders(arr, table);
     for (var i=0, maxi=arr.length; i < maxi; ++i) {
         var tr = _tr_.cloneNode(false);
         for (var j=0, maxj=columns.length; j < maxj ; ++j) {
             var td = _td_.cloneNode(false);
                 cellValue = arr[i][columns[j]];
             td.appendChild(document.createTextNode(arr[i][columns[j]] || ''));
             tr.appendChild(td);
         }
         table.appendChild(tr);
     }

     for (var i = 0 ; i< table.rows.length ; i++){
    // Ajout de la fonction onClick pour chaque tableau
            table.rows[i].onclick = function()
            {
                
                androidShowToast(`the number of ${this.cells[0].innerHTML}   is ${this.cells[1].innerHTML}`);
                
                window.open(`<label for="name">ecire le texte</label> <input type="text" id="name" name="name"  size="20">`);
                   
            }
        } 
     return table;
 }

 function addAllColumnHeaders(arr, table){
     var columnSet = [],
         tr = _tr_.cloneNode(false);
     for (var i=0, l=arr.length; i < l; i++) {
         for (var key in arr[i]) {
             if (arr[i].hasOwnProperty(key) && columnSet.indexOf(key)===-1) {
                 columnSet.push(key);
                 var th = _th_.cloneNode(false);
                 th.appendChild(document.createTextNode(key));
                 tr.appendChild(th);
             }
         }
     }
     table.appendChild(tr);
     return columnSet;
 }






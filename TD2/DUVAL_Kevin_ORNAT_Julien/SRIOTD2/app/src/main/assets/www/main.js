function Toast() {
            Android.showToast("Toast généré en JS");
        }

function TakePicture() {
            Android.takePicture();
        }

function getContacts(){
            let contactsJSON = Android.getContacts();
            let contacts = JSON.parse(contactsJSON);

            // Création d'une variable qui contiendra le contenu du tableau
            var tableContent = "";
            for (let i = 0;i<contacts.length;i++){
                tableContent += "<tr>"; // Nouvelle ligne HTML
                tableContent += "<td>" + contacts[i].name + "</td>" + "<td>" + contacts[i].num + "</td>";
                tableContent += "</tr>"; // Fin de la nouvelle ligne HTML
                }
            // remplacer le contenu du tableau par le contenu de la variable tableContent
            document.getElementById("contacts").innerHTML = tableContent;

            var table = document.getElementById("table"),rIndex;
            		for (var i = 0 ; i< table.rows.length ; i++)
            		{
            			table.rows[i].onclick = function()
            			{
            				rIndex = this.rowsIndex;
                            // Pour récupérer le nom du contact sélectionnée
            				document.getElementById("textAreaName").value = this.cells[0].innerHTML;
            				// mettre le numéro du contact sélectionné dans le text Area "textArea"
                            document.getElementById("textAreaNum").value = this.cells[1].innerHTML;
                            //Android.sendSMS(this.cells[1].innerHTML,"test SMS Java")
            			}
            		}
            /**app.get('/getContactsJSON()', function (req, res) {
                  res.send('GET request to the homepage');
                });
                app.listen(3000);
            **/
        }
function sendSMS(){
        Android.sendSMS(document.getElementById("textAreaNum").value,document.getElementById("textAreaMsg").value)
        }
function composeNum(){
        Android.composeNum(document.getElementById("textAreaNum"))
        }

function getContactsJSON(){
        let contactsJSON = Android.getContacts();
        return contactsJSON
         }
package main

import(
	"html/template"
	"net/http"
)

type template_manager struct{
	templates *template.Template
}

type Page struct { //Has to be adjusted
	title string
	body string
}

func NewTemplateManager(filename1 string, filename2 string, filename3 string) *template_manager{ //Number of files can be adjusted
	tm := template_manager{template.Must(template.ParseFiles(filename1,filename2, filename3))}
	return &tm
}

func (tm *template_manager) displayPage(w http.ResponseWriter, templateName string, pageInformation *Page){
	err := tm.templates.ExecuteTemplate(w, templateName+".html", pageInformation)
	
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}
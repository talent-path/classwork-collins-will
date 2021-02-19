let getCompanies = function() {
    const search = $("#company-name").val();
    
    $.get(
        `http://www.giantbomb.com/api/search?api_key=650103c0b6e822afe3d0790b817c2a1efa2933c5&format=json&query="${search}"&resources=company&field_list=guid,name,image`,
        function(data, textStatus, jqXHR) {

            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);

            $("#status-text").text("Companies Found");

            let resultList = data.results;
            for (let i = 0; i < resultList.length; i++) {
                let newCompany = document.createElement("div");
                newCompany.id = "company" + resultList[i].guid;
                newCompany.class = "CompanyInfo";
                newCompany.style.width = "15%";
                newCompany.style.display = "inline-block";
                newCompany.style.padding = "10px";
                newCompany.style.textAlign = "center";
                let companyImage = document.createElement("img");
                companyImage.src = resultList[i].image.small_url;
                companyImage.style.objectFit = "cover";
                companyImage.style.width = "100%";
                newCompany.appendChild(companyImage);

                newCompany.append(resultList[i].name);
                document.getElementById("result-list").appendChild(newCompany);
            }
            
        }
    )

}
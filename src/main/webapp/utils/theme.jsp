<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!-- Tailwind v4 Dark Mode Support -->

<style type="text/tailwindcss">

@custom-variant dark (&:where(.dark, .dark *));

</style>



<script>


// Load saved theme before page render

(function(){

    const savedTheme = localStorage.getItem("theme");


    if(savedTheme === "dark"){

        document.documentElement.classList.add("dark");

    }


})();




// Global theme toggle function

function toggleTheme(){


    const html = document.documentElement;


    html.classList.toggle("dark");



    if(html.classList.contains("dark")){


        localStorage.setItem(
            "theme",
            "dark"
        );


    }
    else{


        localStorage.setItem(
            "theme",
            "light"
        );


    }


}


</script>
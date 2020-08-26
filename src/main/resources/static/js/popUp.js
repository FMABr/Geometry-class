const signIn = document.getElementById("btLogin");
const signUp = document.getElementById("btCadastro");

const modal = document.querySelector(".container");
const popUp = document.getElementById("entrar");

const fundo = document.querySelectorAll("body > *:not(#entrar)");

const alt = document.getElementById("btAltFormulario");
const formularios = document.querySelectorAll(".container form");

const inTabList = document.querySelectorAll(".container .login input");
const upTabList = document.querySelectorAll(".container .cadastro input");

signIn.addEventListener("click", showLogin, false);
signUp.addEventListener("click", showPopUp, false);

alt.addEventListener("click", alternaFormulario, false);

alt.addEventListener("transitionend", (event) => {
  if(event.propertyName === "left")
  if (alt.classList.contains("alt")) {
    inTabList[1].focus();
  } else {
    upTabList[1].focus();
  }
}, false);

alt.addEventListener("keydown", (event) => {
  if(event.key === "Tab") {
    event.preventDefault();
    
    if(alt.classList.contains("alt")) {
      if(event.shiftKey) {
        inTabList[inTabList.length -1].focus();
      } else {
        inTabList[1].focus();
      }
    } else {
      if(event.shiftKey) {
        upTabList[upTabList.length -1].focus();
      } else {
        upTabList[1].focus();
      }
    }
  }
}, false);

upTabList[1].addEventListener("keydown", (event) => {
  if(event.key === "Tab" && event.shiftKey) {
    event.preventDefault();
    alt.focus();
  }
}, false);

inTabList[inTabList.length - 1].addEventListener("keydown", (event) => {
  if(event.key === "Tab" && !event.shiftKey) {
    event.preventDefault();
    alt.focus();
  }
}, false);

function showLogin() {
  alternaFormulario();
  showPopUp();

  inTabList[1].focus();
}

function showPopUp() {
  for (const elemento of fundo) {
    elemento.classList.add("blurry");
  }

  hideOnClickOutside();

  popUp.classList.remove("off");

  upTabList[1].focus();
}

function hidePopUp() {
  for (const elemento of fundo) {
    elemento.classList.remove("blurry");
  }

  for (const form of formularios) {
    form.classList.remove("alt");
  }

  alt.firstChild.classList.remove("alt");
  alt.classList.remove("alt");

  popUp.classList.add("off");
}

function hideOnClickOutside() {
  const outsideClickListener = event => {
    if (!modal.contains(event.target)) {
      hidePopUp();
      popUp.removeEventListener('click', outsideClickListener);
    }
  }

  popUp.addEventListener('click', outsideClickListener);
}

function alternaFormulario() {
  alt.classList.toggle("alt");
  alt.firstChild.classList.toggle("alt");

  for (const form of formularios) {
    form.classList.toggle("alt");
  }
}

if(location.hash === "#login-erro" || location.hash === "#login"){
  signIn.click();
} else if(location.pathname === "/cadastro") {
  signUp.click();
}

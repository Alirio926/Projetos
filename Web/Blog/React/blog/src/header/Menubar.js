import React, { Component } from "react";
import './Menubar.css';


class MenuBar extends Component{
    render(){
        return(
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
                <div className="container">
                <a className="navbar-brand cor-icone" href="/">Squall Blog</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav ml-auto flex-row fa-ul">                            
                        <li className="nav-item fa-li"><a className="nav-link px-2 fa-lg" href="/"><span className="fa fa-facebook-official" style={{color: "#ff922b"}}></span></a></li>
                        <li className="nav-item fa-li"><a className="nav-link px-2 fa-lg" href="/"><span className="fa fa-twitter cor-icone"></span></a></li>
                        <li className="nav-item fa-li"><a className="nav-link px-2 fa-lg" href="/"><span className="fa fa-youtube cor-icone"></span></a></li>
                        <li className="nav-item fa-li"><a className="nav-link px-2 fa-lg" href="/"><span className="fa fa-linkedin cor-icone"></span></a></li>
                        <li className="nav-item fa-li"><a className="nav-link px-2 fa-lg" href="/"><span className="fa fa-sign-in cor-icone"></span></a></li>
                    </ul>
                </div>
                </div>
            </nav>
        )
    }
} 
 
export default MenuBar;
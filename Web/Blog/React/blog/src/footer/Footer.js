import React, { Component } from "react";
import './Footer.css';

class Footer extends Component{
    render(){
        return(
            <div>
                <footer className="py-5 bg-dark">
                    <div className="container">
                    <p className="m-0 text-center text-white">Copyright &copy; <a href="http://squallsoft.com">SquallSoft</a> 2020</p>
                    </div>
                    
                </footer>
            </div>
        )
    }
}
export default Footer;
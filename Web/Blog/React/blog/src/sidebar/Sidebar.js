import React, { Component } from "react";

import './Sidebar.css';

class Sidebar extends Component{
    render(){
        return(

            
            <div className="sticky-top"> 
                <div><br></br><br></br>
                </div>
                <div className="card my-2">
                    <h5 className="card-header">Procurar por conteúdo</h5>
                    <div className="card-body">
                    <div className="input-group">
                        <input type="text" className="form-control" placeholder="Procurar categoria..." />
                        <span className="input-group-btn">
                            <button className="btn btn-secondary mx-1 " type="button">Pesquisar</button>
                        </span>
                    </div>
                    </div>
                </div>

                <div className="card my-4">
                    <h5 className="card-header">Categorias</h5>
                    <div className="card-body">
                        <div className="row">
                            <div className="col-lg-6">
                                <ul className="list-unstyled mb-0">
                                <li>
                                    <a href="//">Web Design</a>
                                </li>
                                <li>
                                    <a href="/">HTML</a>
                                </li>
                                <li>
                                    <a href="/">Freebies</a>
                                </li>
                                </ul>
                            </div>
                            <div className="col-lg-6">
                                <ul className="list-unstyled mb-0">
                                <li>
                                    <a href="/">JavaScript</a>
                                </li>
                                <li>
                                    <a href="/">CSS</a>
                                </li>
                                <li>
                                    <a href="/">Tutorials</a>
                                </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="card my-4">
                    <h5 className="card-header">Arquivos</h5>
                    <div className="card-body">
                    <iframe src="http://meuip.datahouse.com.br/meuip-site.php" name="meuip" width="100" height="132" scrolling="No"   frameborder="0" id="meuip"></iframe>
                    <p className="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus possimus, veniam magni quis!</p>
                    </div>
                </div>
                
                

            </div>
        )
    }
}
export default Sidebar;
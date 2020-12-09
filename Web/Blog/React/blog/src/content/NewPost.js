import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

import {newPost} from './../services/PostService';
import {URL_API} from './../services/Base';

import * as $ from './../include/jquery';
import ReactSummernote from 'react-summernote';
import 'react-summernote/dist/react-summernote.css'; // import styles
import 'react-summernote/lang/summernote-pt-BR'; // you can import any other locale
 

class RichTextEditor  extends Component{
    
    state = {
        redirect: false
    }
    setRedirect = () => {
        this.setState({
            redirect: true
        })
    }
    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={"/"}/>
        }
    }
    constructor(props){
        super(props);
        this.state = { 
            postagens : [{
                id              : 0,
                titulo          : " ",
                texto           : " ",
                permalink       : " ",
                dataPostagem    :[
                    2020,
                    3,
                    29,
                    12,
                    19,
                    15
                ],
                autorId         : 1,
                autorNome       : " ",
                autorBiografia  : " ",
                categorias      : [{
                    id          : 1,
                    descricao   : " ",
                    permalink   : " "
                }],
            }] ,         
            richText: "",
            value_default: null
          }

        this.handleSubmit = this.handleSubmit.bind(this);
    }
    onChange = (content) => {
        this.setState({
          richText: content          
        })
    }
    componentDidMount(){
        const { handle } = this.props.match.params;
        if(handle !== null){
            const permalink_url = URL_API + "/post/show/" + handle;

            fetch(`${permalink_url}`)
                .then((resultado) => resultado.json()).then(resultado => this.setState(resultado))
            
        }
    }
    handleSubmit(event){
        if(!event.target.checkValidity()){
            return;
        }
        event.preventDefault();

        const data = new FormData(event.target);      

        newPost(data);
        
        this.setRedirect();
    }
    render(){  
        function LoadTitle(props){
            const id = props.id;
            console.log("id: "+props.id);
            if(id > 0){
                return(
                    <h1 className="my-4" id="page-title" ><b>Editando Post</b></h1>
                )
            }        
            return(
                <h1 className="my-4" id="page-title" ><b>Novo Post</b></h1>
            )
        }      
        return(           
            <div>
                {this.renderRedirect()}
                <div><br></br></div>
                <LoadTitle id={this.state.postagens.id}/>
                <div className="card mb-4">
                    <div className="card-body">
                        <form className="form-horizontal was-validated" onSubmit={this.handleSubmit}>                            
                            <div className="form-group">
                                <label htmlFor="titulo-post"><h5>Nome do Post</h5></label>
                                <input value={this.state.postagens.titulo} name="titulo" type="text" className="form-control is-invalid" id="titulo-post" arial-describedly="titleHelp" placeholder="Digite o titulo do post" required></input>
                                <small id="titleHelp" className="form-text text-muted">Relacionado ao assunto da postagem.</small>
                            </div>
                            <div className="form-group">
                                <label htmlFor="texto-post"><h5>Conteúdo do Post</h5></label>
                                <textarea id="text-hidden" name="texto" className="form-control is-invalid" value={this.state.richText} hidden required></textarea>
                                {/*<textarea id="textfield" name="content"  placeholder="Content" className="form-control editor1"></textarea>*/}
                                <ReactSummernote
                                    id="summer"
                                    value={this.state.postagens.texto}
                                    options={{
                                    lang: 'pt-BR',
                                    height: 280,
                                    dialogsInBody: true,
                                    toolbar: [
                                        ['style', ['style']],
                                        ['font', ['bold', 'underline', 'clear']],
                                        ['fontname', ['fontname']],
                                        ['para', ['ul', 'ol', 'paragraph']],
                                        ['table', ['table']],
                                        ['insert', ['link', 'picture', 'video']],
                                        ['view', ['fullscreen', 'codeview']]
                                    ]
                                    }}onChange={this.onChange}
                                    shouldComponentUpdate={() => {}}
                                    />
                            </div>
                            <input name="id" hidden value={this.state.postagens.id}></input>
                            <input type="submit" value="Publicar  &rarr;" className="btn btn-primary" />
                        </form>                        
                    </div>                    
                </div>
            </div>
        ) 
    }
}
export default RichTextEditor ;
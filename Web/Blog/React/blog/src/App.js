import React from 'react';
import {Switch, Route } from 'react-router-dom';
import './App.css';

import MenuBar from './header/Menubar'
import Sidebar from './sidebar/Sidebar'
import MainPost from './content/MainPost'
import NewPost from './content/NewPost'
import ShowPost from './content/ShowPost'
import Footer from './footer/Footer'

function App() {
  return (
    <div>
      <div className="container">
        <div className="container">
          <MenuBar />
        </div>
        <div className="row content">
          <div className="col-md-8">
            <Switch>
              {/* Edit Page post */}
              <Route path="/newpost/:handle" exect component={NewPost} />   
              {/* New Page post */}
              <Route path="/newpost" exect component={NewPost} />               
              {/* Show Page post */}
              <Route path="/post/:handle" exect component={ShowPost} />
              {/* Main Page post */}
              <Route path="/" exect component={MainPost} />
            </Switch>
          </div>
          <div className="col-md-4"> 
            <Sidebar />
          </div>
        </div>       
      </div>
      <div>
        <Footer />
      </div>
    </div>
  );
}

export default App;

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './app/App.jsx'
import store from './app/store/store.js'
import { Provider } from 'react-redux';
import './app/locales/i18n.js';
import { applyInterceptors } from './app/util/interseptors.js';

applyInterceptors(store)


createRoot(document.getElementById('root')).render(
  
    <Provider store={store}>
    <App />
    </Provider>
 
)

import { createBrowserRouter, RouterProvider } from "react-router";
import Layout from "./layout/ui/Layout";
import Page404 from "../pages/404/ui/Page404";
import { PathConstants } from "./pathConstants";
import Articles from "../pages/Article/ui/Articles";
import Profile from "../pages/Profile/ui/Profile";
import CreateArticle from "../pages/CreateArticle/ui/CreateArticle";
import ArticleView from "../pages/ArticleView/ui/ArticleView";
import ManageArticle from "../pages/ManagerArticle/ui/ManageArticle";
import Login from "../pages/Login/ui/Login";
import ProfileArticle from "../pages/Profile/ui/ProfileArticle";
import SearchPage from "../pages/SearchPage/ui/SearchPage";



function App() {
  const router = createBrowserRouter([
    {
      element: <Layout />,
      errorElement: <Page404 />,
      children: [
        {
          path: PathConstants.HOME,
          element: <Login />,
        },
        {
          path: PathConstants.ARTICLE,
          element: <Articles />,
        },
        {
          path: PathConstants.CREATE_ARTICLE,
          element: <CreateArticle />,
        },
        {
          path: PathConstants.PROFILE,
          element: <Profile />,
        },
        {
          path: PathConstants.VIEW,
          element: <ArticleView />,
        },
        {
          path: PathConstants.MANAGE_ARTICLES,
          element: <ManageArticle />,
        },
        {
          path:PathConstants.LOGIN,
          element:<Login />,
        },
        {
          path:PathConstants.PROFILE_ARTICLE,
          element:<ProfileArticle />,
        },
        {
          path:PathConstants.SEARCH,
          element:<SearchPage />,
        }

     
      ],
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App
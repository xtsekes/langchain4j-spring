import MainView from "Frontend/views/MainView.js";
import {
    createBrowserRouter,
    RouteObject
} from "react-router-dom";
import ChatView from "Frontend/views/chat/ChatView";
import StreamChatView from "Frontend/views/streamchat/StreamChatView";
import StreamAssistantView from "Frontend/views/streamassistant/StreamAssistantView";

export const routes: readonly RouteObject[] = [
    {
        path: "/",
        element: <MainView />,
        children: [
            { path: '/chat', element: <ChatView /> },
            { path: '/stream-chat', element: <StreamChatView /> },
            { path: '/stream-assistant', element: <StreamAssistantView /> },
        ],
    },
];

export const router = createBrowserRouter([...routes], {basename: new URL(document.baseURI).pathname });

import { ChatController } from "Frontend/generated/endpoints";
import { MessageInput } from "@hilla/react-components/MessageInput";
import { Notification } from "@hilla/react-components/Notification";
import {MessageList} from "@hilla/react-components/MessageList";

export default function IngestDataView() {

    async function sendMessage(message: string) {
        try {
            await ChatController.ingestInfo(message);
            Notification.show("Data ingested successfully!", { position: "middle", duration: 3000 });
        } catch (error) {
            Notification.show("Failed to ingest data.", { position: "middle", duration: 3000 });
        }
    }

    return (
        <div className="p-m flex flex-col h-full box-border">
            <MessageList className="flex-grow"/>
            <MessageInput
                onSubmit={e => sendMessage(e.detail.value)}
            />
        </div>
    );
}
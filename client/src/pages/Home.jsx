import { useState } from "react";
import { useAuth } from "../context/auth-context";
import CardService from "../services/CardService";
import CardList from "../components/CardList";

export default function Home() {
  const { user, token } = useAuth();
  const [importStatus, setImportStatus] = useState("idle");

  const confirmImport = async () => {
    if (confirm("Really do dis?")) {
      try {
        setImportStatus("importing");
        await CardService.import();
        setImportStatus("done");
      } catch (error) {
        console.error("Import failed", error);
        setImportStatus("failed");
      }
    }
  }

  return (
    <CardList
      fetchData={CardService.list}
      linkFunction={(card) => `/cards/${card.id}`}
      titleBlurb={
      <>
        <p>This is the home page.</p>
        <p>{token ? `Welcome, ${user.username}!` : "You are not logged in."}</p>
        {user?.role==="ROLE_ADMIN" &&
          <button className="border-1 rounded-sm" onClick={() => confirmImport()}>
            <p className="m-[1px]">
              {importStatus === "idle" && <>Import from PokeAPI</>}
              {importStatus === "importing" && <>Importing...</>}
              {importStatus === "done" && <>Import finished!</>}
              {importStatus === "failed" && <>Import failed.</>}
            </p>
          </button>
        }
      </>}
    />
  );
}
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getPawnById } from "../../services/pawnService";

interface Pawn {
  pawnId: string;
  platformIdentifier: string;
  vocations: string;
  name: string;
  level: number;
  inclinations: string;
  specializations: string;
  notes: string;
  gender: string;
  imageUrl: string;
  createdBy: string;
}

export default function PawnPage() {
  const { id } = useParams();
  const [pawn, setPawn] = useState<Pawn | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchPawn = async () => {
      if (!id) return;
      try {
        setLoading(true);
        const data = await getPawnById(id);
        setPawn(data);
      } catch (error) {
        if (error.response && error.response.data) {
          console.log(error.response.data.message || "Failed to fetch pawn");
        }
      } finally {
        setLoading(false);
      }
    };
    fetchPawn();
  }, [id]);

  if (loading) return <p>Loading...</p>
  if (!pawn) return <p>Pawn not found.</p>

  return (
  <div className="min-h-screen bg-zinc-900 text-yellow-200 font-sans flex justify-center items-start pt-12">
    <div className="p-6 max-w-3xl bg-zinc-800 border border-yellow-600 rounded-lg shadow-lg">

      <h1 className="text-3xl font-extrabold mb-6 text-yellow-300 drop-shadow-md text-center">
        {pawn.name}
      </h1>

      <div className="space-y-3 text-yellow-100">
        <p><span className="font-semibold text-yellow-300">Created by:</span> {pawn.createdBy}</p>
        <p><span className="font-semibold text-yellow-300">Pawn ID:</span> {pawn.pawnId}</p>
        <p><span className="font-semibold text-yellow-300">Pawn name:</span> {pawn.name}</p>
        <p><span className="font-semibold text-yellow-300">Gender:</span> {pawn.gender}</p>
        <p><span className="font-semibold text-yellow-300">Level:</span> {pawn.level}</p>
        <p><span className="font-semibold text-yellow-300">Vocation:</span> {pawn.vocations}</p>
        <p><span className="font-semibold text-yellow-300">Inclinations:</span> {pawn.inclinations}</p>
        <p><span className="font-semibold text-yellow-300">Specialization:</span> {pawn.specializations}</p>
        <p className="max-h-24 overflow-auto break-words pr-2"><span className="font-semibold text-yellow-300">Notes:</span> {pawn.notes}
</p>        <p><span className="font-semibold text-yellow-300">Platform ID:</span> {pawn.platformIdentifier}</p>
      </div>

      {pawn.imageUrl && (
        <div className="mt-6">
          <img
            src={pawn.imageUrl}
            alt={`${pawn.name}`}
            className="w-full h-auto rounded-lg border border-yellow-500 shadow-md"
          />
        </div>
      )}
    </div>
  </div>
);
}

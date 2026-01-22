import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { AlertCircle, CheckCircle2, MessageSquare, ShieldCheck, RefreshCw } from 'lucide-react';

const api = axios.create({ baseURL: 'http://localhost:8080/api/reconciliation' });

function App() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadData = async () => {
    setLoading(true);
    try {
      const res = await api.get('/report');
      setData(res.data);
    } catch (e) {
      alert("Errore nel caricamento dei dati. Controlla la console per i dettagli."); 
      console.error("Errore API", e); 
    }
    setLoading(false);
  };

  useEffect(() => { loadData(); }, []);

  const handleValidate = async (row) => {
    const note = window.prompt(`Nota di riconciliazione per ${row.securityName}:`);
    if (note !== null) {
      try {
        await api.post('/validate', { isin: row.isin, portfolioId: row.portfolioId, note });
        loadData();
      } catch (e) {
        alert("Errore nell'invio della validazione. Controlla la console per i dettagli.");
        console.error("Errore API", e);
      }
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 p-8 font-sans">
      <div className="max-w-6xl mx-auto">
        <div className="flex justify-between items-end mb-8">
          <div>
            <h1 className="text-3xl font-bold text-slate-900 tracking-tight">H14 Portfolio Recon</h1>
            <p className="text-slate-500 mt-1">Stato allineamento Swarm vs Custodian Banks</p>
          </div>
          <button onClick={loadData} className="flex items-center gap-2 bg-white px-4 py-2 rounded-lg border shadow-sm hover:bg-slate-50 transition-all">
            <RefreshCw size={16} className={loading ? "animate-spin" : ""} /> Aggiorna
          </button>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
          <table className="w-full text-left">
            <thead className="bg-slate-50 border-b border-slate-200">
              <tr>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase">Security</th>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase text-right">Internal Qty</th>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase text-right">Bank Qty</th>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase text-right">Delta</th>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase">Status</th>
                <th className="px-6 py-4 text-xs font-semibold text-slate-500 uppercase text-center">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100">
              {data.map((row) => {
                const hasMismatch = Math.abs(row.diffQty) > 0.0001;
                return (
                  <tr key={row.isin} className="hover:bg-slate-50/50 transition-colors">
                    <td className="px-6 py-4">
                      <div className="font-semibold text-slate-800">{row.securityName}</div>
                      <div className="text-xs font-mono text-slate-400">{row.isin}</div>
                    </td>
                    <td className="px-6 py-4 text-right font-mono text-sm">{row.internalQty?.toLocaleString()}</td>
                    <td className="px-6 py-4 text-right font-mono text-sm">{row.bankQty?.toLocaleString()}</td>
                    <td className={`px-6 py-4 text-right font-mono text-sm font-bold ${hasMismatch ? 'text-red-500' : 'text-emerald-500'}`}>
                      {hasMismatch ? row.diffQty.toLocaleString() : '—'}
                    </td>
                    <td className="px-6 py-4">
                      {row.manuallyValidated ? (
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-50 text-blue-700 border border-blue-100">
                          <ShieldCheck size={12} className="mr-1" /> Manual OK
                        </span>
                      ) : !hasMismatch ? (
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-emerald-50 text-emerald-700 border border-emerald-100">
                          <CheckCircle2 size={12} className="mr-1" /> Matched
                        </span>
                      ) : (
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-amber-50 text-amber-700 border border-amber-100">
                          <AlertCircle size={12} className="mr-1" /> Mismatch
                        </span>
                      )}
                    </td>
                    <td className="px-6 py-4 text-center">
                      {hasMismatch && !row.manuallyValidated && (
                        <button onClick={() => handleValidate(row)} className="text-slate-400 hover:text-blue-600 transition-colors p-1">
                          <MessageSquare size={18} />
                        </button>
                      )}
                      {row.notes && <div className="text-[10px] text-slate-400 italic mt-1 max-w-[120px] truncate">{row.notes}</div>}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default App;
import React, { useState, useEffect } from 'react';
import { 
  Mic, MicOff, Volume2, VolumeX, Cpu, Database, History, Brain, 
  Activity, ShieldCheck, Zap, Settings, Play, Square, RefreshCw, 
  CheckCircle, AlertTriangle, Layers, Smartphone, Terminal, Search
} from 'lucide-react';

export default function App() {
  const [activeTab, setActiveTab] = useState<'assistant' | 'history' | 'memory' | 'engines' | 'diagnostics'>('assistant');
  
  // Voice Assistant State
  const [isListening, setIsListening] = useState(false);
  const [isSpeaking, setIsSpeaking] = useState(false);
  const [pipelineState, setPipelineState] = useState<'IDLE' | 'LISTENING' | 'PROCESSING' | 'SPEAKING'>('IDLE');
  const [noiseSuppressionEnabled, setNoiseSuppressionEnabled] = useState(true);
  const [bargeInEnabled, setBargeInEnabled] = useState(true);
  const [continuousListening, setContinuousListening] = useState(true);
  const [transcripts, setTranscripts] = useState<Array<{id: string, sender: 'user' | 'rider', text: string, timestamp: string}>>([
    { id: '1', sender: 'rider', text: 'RIDER Voice System initialized. Ready for voice commands.', timestamp: '10:00:05' },
    { id: '2', sender: 'user', text: 'Play my favorite morning playlist and set alarm for 7 AM.', timestamp: '10:01:12' },
    { id: '3', sender: 'rider', text: 'Playing morning playlist on Spotify and setting alarm for 7:00 AM.', timestamp: '10:01:13' }
  ]);
  const [inputText, setInputText] = useState('');

  // Conversation History State (Feature #40)
  const [sessions, setSessions] = useState([
    { id: 'sess_1', title: 'Morning Commute & Playlist', date: '2026-10-18 08:30', messageCount: 12 },
    { id: 'sess_2', title: 'Smart Home Control', date: '2026-10-18 07:15', messageCount: 8 },
    { id: 'sess_3', title: 'Weather & Traffic Query', date: '2026-10-17 18:40', messageCount: 5 }
  ]);
  const [selectedSessionId, setSelectedSessionId] = useState('sess_1');

  // Persistent Memory State (Feature #41)
  const [memories, setMemories] = useState([
    { id: 'mem_1', key: 'user_preferred_music', value: 'Synthwave & Lo-Fi Beats', category: 'Preferences', updatedAt: '2026-10-18' },
    { id: 'mem_2', key: 'home_location', value: '123 Silicon Valley Way', category: 'Locations', updatedAt: '2026-10-15' },
    { id: 'mem_3', key: 'default_alarm_time', value: '07:00 AM', category: 'Routine', updatedAt: '2026-10-16' },
  ]);
  const [newMemoryKey, setNewMemoryKey] = useState('');
  const [newMemoryValue, setNewMemoryValue] = useState('');

  // Modular Engines State (Feature #72, #73, #74, #79)
  const [ramTier, setRamTier] = useState<'8GB_HIGH' | '6GB_MEDIUM' | '4GB_LOW'>('8GB_HIGH');
  const [selectedSttEngine, setSelectedSttEngine] = useState('SherpaOnnxSttEngine (WenetConformer)');
  const [selectedTtsEngine, setSelectedTtsEngine] = useState('SherpaOnnxTtsEngine (VITS Piper)');
  const [quantizationType, setQuantizationType] = useState('INT8 (Optimal Balance)');

  // Latency & Metrics State (Feature #34, #76)
  const [metrics, setMetrics] = useState({
    vadLatencyMs: 42,
    sttLatencyMs: 185,
    llmLatencyMs: 310,
    ttsLatencyMs: 120,
    totalLatencyMs: 657,
    budgetLimitMs: 800
  });

  const handleSendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputText.trim()) return;
    
    const userMsg = {
      id: Date.now().toString(),
      sender: 'user' as const,
      text: inputText,
      timestamp: new Date().toLocaleTimeString()
    };
    
    setTranscripts(prev => [...prev, userMsg]);
    setInputText('');
    setPipelineState('PROCESSING');

    setTimeout(() => {
      setPipelineState('SPEAKING');
      setIsSpeaking(true);
      const riderMsg = {
        id: (Date.now() + 1).toString(),
        sender: 'rider' as const,
        text: `Processed command: "${userMsg.text}". Executing action successfully.`,
        timestamp: new Date().toLocaleTimeString()
      };
      setTranscripts(prev => [...prev, riderMsg]);
      
      setTimeout(() => {
        setPipelineState(continuousListening ? 'LISTENING' : 'IDLE');
        setIsSpeaking(false);
      }, 2500);
    }, 1200);
  };

  const toggleListening = () => {
    if (isListening) {
      setIsListening(false);
      setPipelineState('IDLE');
    } else {
      setIsListening(true);
      setPipelineState('LISTENING');
    }
  };

  const addMemory = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newMemoryKey || !newMemoryValue) return;
    setMemories(prev => [
      ...prev,
      {
        id: 'mem_' + Date.now(),
        key: newMemoryKey,
        value: newMemoryValue,
        category: 'Custom',
        updatedAt: new Date().toISOString().split('T')[0]
      }
    ]);
    setNewMemoryKey('');
    setNewMemoryValue('');
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 flex flex-col">
      {/* Top Header */}
      <header className="border-b border-slate-800 bg-slate-900/80 backdrop-blur sticky top-0 z-50 px-6 py-4 flex items-center justify-between">
        <div className="flex items-center space-x-3">
          <div className="bg-indigo-600 p-2 rounded-xl text-white shadow-lg shadow-indigo-500/30">
            <Smartphone className="w-6 h-6" />
          </div>
          <div>
            <h1 className="text-xl font-bold tracking-tight text-white flex items-center gap-2">
              RIDER Voice System <span className="text-xs bg-indigo-500/20 text-indigo-400 px-2 py-0.5 rounded-full border border-indigo-500/30 font-medium">Android GVP</span>
            </h1>
            <p className="text-xs text-slate-400">Offline-First Voice Assistant & Modular Pipeline Control</p>
          </div>
        </div>

        <div className="flex items-center space-x-4">
          <div className="hidden md:flex items-center space-x-2 bg-slate-800/60 px-3 py-1.5 rounded-lg border border-slate-700/50 text-xs">
            <span className={`w-2.5 h-2.5 rounded-full ${isListening ? 'bg-emerald-400 animate-pulse' : 'bg-slate-500'}`} />
            <span className="font-medium text-slate-300">Pipeline State:</span>
            <span className="font-mono text-indigo-400 font-bold">{pipelineState}</span>
          </div>

          <button 
            onClick={toggleListening}
            className={`flex items-center space-x-2 px-4 py-2 rounded-xl font-medium text-sm transition-all shadow-lg ${
              isListening 
                ? 'bg-rose-600 hover:bg-rose-500 text-white shadow-rose-600/30' 
                : 'bg-indigo-600 hover:bg-indigo-500 text-white shadow-indigo-600/30'
            }`}
          >
            {isListening ? <MicOff className="w-4 h-4" /> : <Mic className="w-4 h-4" />}
            <span>{isListening ? 'Stop Listening' : 'Start Listening'}</span>
          </button>
        </div>
      </header>

      {/* Main Navigation Tabs */}
      <nav className="bg-slate-950 border-b border-slate-800 px-6 flex space-x-1 overflow-x-auto">
        {[
          { id: 'assistant', label: 'Voice Control Center', icon: Mic },
          { id: 'history', label: 'Conversation History (#40)', icon: History },
          { id: 'memory', label: 'Persistent Memory (#41)', icon: Brain },
          { id: 'engines', label: 'Modular Engines & RAM', icon: Cpu },
          { id: 'diagnostics', label: 'Latency & State Machine', icon: Activity },
        ].map(tab => {
          const Icon = tab.icon;
          const isActive = activeTab === tab.id;
          return (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id as any)}
              className={`flex items-center space-x-2 px-4 py-3.5 border-b-2 font-medium text-sm transition-colors whitespace-nowrap ${
                isActive 
                  ? 'border-indigo-500 text-indigo-400 bg-indigo-500/5' 
                  : 'border-transparent text-slate-400 hover:text-slate-200 hover:bg-slate-900/40'
              }`}
            >
              <Icon className="w-4 h-4" />
              <span>{tab.label}</span>
            </button>
          );
        })}
      </nav>

      {/* Content Area */}
      <main className="flex-1 p-6 max-w-7xl w-full mx-auto">
        {activeTab === 'assistant' && (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Live Conversation Stream */}
            <div className="lg:col-span-2 bg-slate-900/60 border border-slate-800/80 rounded-2xl flex flex-col h-[650px] shadow-xl backdrop-blur">
              <div className="px-6 py-4 border-b border-slate-800 flex items-center justify-between">
                <div className="flex items-center space-x-2">
                  <Terminal className="w-5 h-5 text-indigo-400" />
                  <h2 className="font-semibold text-slate-200">Live Voice & Text Interaction</h2>
                </div>
                <div className="flex items-center space-x-3 text-xs text-slate-400">
                  <span className="flex items-center gap-1">
                    <ShieldCheck className="w-3.5 h-3.5 text-emerald-400" /> VAD Active
                  </span>
                  <span className="flex items-center gap-1">
                    <Zap className="w-3.5 h-3.5 text-amber-400" /> Barge-in Enabled
                  </span>
                </div>
              </div>

              <div className="flex-1 p-6 overflow-y-auto space-y-4">
                {transcripts.map((msg) => (
                  <div 
                    key={msg.id} 
                    className={`flex flex-col ${msg.sender === 'user' ? 'items-end' : 'items-start'}`}
                  >
                    <div className="flex items-center space-x-2 mb-1">
                      <span className="text-xs font-medium text-slate-400">
                        {msg.sender === 'user' ? 'User' : 'RIDER Assistant'}
                      </span>
                      <span className="text-[10px] text-slate-600">{msg.timestamp}</span>
                    </div>
                    <div className={`max-w-md px-4 py-3 rounded-2xl text-sm leading-relaxed ${
                      msg.sender === 'user' 
                        ? 'bg-indigo-600 text-white rounded-br-none shadow-md shadow-indigo-600/20' 
                        : 'bg-slate-800 text-slate-100 rounded-bl-none border border-slate-700/60 shadow-md'
                    }`}>
                      {msg.text}
                    </div>
                  </div>
                ))}
              </div>

              <form onSubmit={handleSendMessage} className="p-4 border-t border-slate-800 bg-slate-900/90 rounded-b-2xl flex items-center space-x-3">
                <input
                  type="text"
                  value={inputText}
                  onChange={(e) => setInputText(e.target.value)}
                  placeholder="Type a voice command or query..."
                  className="flex-1 bg-slate-950 border border-slate-700/60 rounded-xl px-4 py-3 text-sm focus:outline-none focus:border-indigo-500 text-slate-100 placeholder-slate-500"
                />
                <button
                  type="submit"
                  className="bg-indigo-600 hover:bg-indigo-500 text-white px-5 py-3 rounded-xl font-medium text-sm transition-colors shadow-lg shadow-indigo-600/30"
                >
                  Send
                </button>
              </form>
            </div>

            {/* Pipeline Controls & Settings */}
            <div className="space-y-6">
              <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
                <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                  <Settings className="w-4 h-4 text-indigo-400" /> Pipeline Settings
                </h3>
                <div className="space-y-4 text-sm">
                  <label className="flex items-center justify-between p-3 bg-slate-950/60 rounded-xl border border-slate-800 cursor-pointer">
                    <div>
                      <div className="font-medium text-slate-200">Noise Suppression (WebRTC APM)</div>
                      <div className="text-xs text-slate-400">Filter background cabin noise</div>
                    </div>
                    <input 
                      type="checkbox" 
                      checked={noiseSuppressionEnabled} 
                      onChange={() => setNoiseSuppressionEnabled(!noiseSuppressionEnabled)}
                      className="w-4 h-4 accent-indigo-600 rounded"
                    />
                  </label>

                  <label className="flex items-center justify-between p-3 bg-slate-950/60 rounded-xl border border-slate-800 cursor-pointer">
                    <div>
                      <div className="font-medium text-slate-200">TTS Barge-in Interruption</div>
                      <div className="text-xs text-slate-400">Stop TTS when speech detected</div>
                    </div>
                    <input 
                      type="checkbox" 
                      checked={bargeInEnabled} 
                      onChange={() => setBargeInEnabled(!bargeInEnabled)}
                      className="w-4 h-4 accent-indigo-600 rounded"
                    />
                  </label>

                  <label className="flex items-center justify-between p-3 bg-slate-950/60 rounded-xl border border-slate-800 cursor-pointer">
                    <div>
                      <div className="font-medium text-slate-200">Continuous Listening (Auto VAD)</div>
                      <div className="text-xs text-slate-400">Keep listening after response</div>
                    </div>
                    <input 
                      type="checkbox" 
                      checked={continuousListening} 
                      onChange={() => setContinuousListening(!continuousListening)}
                      className="w-4 h-4 accent-indigo-600 rounded"
                    />
                  </label>
                </div>
              </div>

              <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
                <h3 className="font-semibold text-slate-200 mb-3 flex items-center gap-2">
                  <Activity className="w-4 h-4 text-emerald-400" /> Live Latency Monitor
                </h3>
                <div className="space-y-3 text-xs">
                  <div className="flex justify-between py-1 border-b border-slate-800">
                    <span className="text-slate-400">VAD Trigger:</span>
                    <span className="font-mono font-medium text-slate-200">{metrics.vadLatencyMs} ms</span>
                  </div>
                  <div className="flex justify-between py-1 border-b border-slate-800">
                    <span className="text-slate-400">STT Recognition:</span>
                    <span className="font-mono font-medium text-slate-200">{metrics.sttLatencyMs} ms</span>
                  </div>
                  <div className="flex justify-between py-1 border-b border-slate-800">
                    <span className="text-slate-400">LLM Generation:</span>
                    <span className="font-mono font-medium text-slate-200">{metrics.llmLatencyMs} ms</span>
                  </div>
                  <div className="flex justify-between py-1 border-b border-slate-800">
                    <span className="text-slate-400">TTS Synthesis:</span>
                    <span className="font-mono font-medium text-slate-200">{metrics.ttsLatencyMs} ms</span>
                  </div>
                  <div className="flex justify-between pt-1 font-bold text-sm">
                    <span className="text-indigo-400">Total Roundtrip:</span>
                    <span className="font-mono text-emerald-400">{metrics.totalLatencyMs} ms / {metrics.budgetLimitMs} ms budget</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'history' && (
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
              <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                <History className="w-4 h-4 text-indigo-400" /> Conversation Sessions
              </h3>
              <div className="space-y-2">
                {sessions.map(s => (
                  <div
                    key={s.id}
                    onClick={() => setSelectedSessionId(s.id)}
                    className={`p-3.5 rounded-xl border cursor-pointer transition-all ${
                      selectedSessionId === s.id 
                        ? 'bg-indigo-600/10 border-indigo-500/50 text-white' 
                        : 'bg-slate-950/60 border-slate-800 text-slate-300 hover:border-slate-700'
                    }`}
                  >
                    <div className="font-medium text-sm">{s.title}</div>
                    <div className="flex justify-between items-center mt-2 text-xs text-slate-400">
                      <span>{s.date}</span>
                      <span className="bg-slate-800 px-2 py-0.5 rounded-full">{s.messageCount} msgs</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            <div className="md:col-span-2 bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur flex flex-col h-[600px]">
              <div className="flex justify-between items-center pb-4 border-b border-slate-800 mb-4">
                <div>
                  <h3 className="font-semibold text-slate-200">Room DB Session Inspector</h3>
                  <p className="text-xs text-slate-400">Viewing messages stored in SQLite Room Database (Feature #40)</p>
                </div>
                <span className="text-xs bg-emerald-500/10 text-emerald-400 px-3 py-1 rounded-full border border-emerald-500/30 font-medium">
                  Synced
                </span>
              </div>

              <div className="flex-1 overflow-y-auto space-y-3 pr-2">
                {transcripts.map((t, idx) => (
                  <div key={idx} className="p-4 bg-slate-950/70 border border-slate-800/80 rounded-xl">
                    <div className="flex justify-between text-xs text-slate-400 mb-1">
                      <span className="font-semibold text-indigo-400 uppercase">{t.sender}</span>
                      <span>{t.timestamp}</span>
                    </div>
                    <p className="text-sm text-slate-200">{t.text}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {activeTab === 'memory' && (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
              <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                <Brain className="w-4 h-4 text-indigo-400" /> Store Persistent Memory
              </h3>
              <form onSubmit={addMemory} className="space-y-4">
                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1">Memory Key</label>
                  <input
                    type="text"
                    value={newMemoryKey}
                    onChange={(e) => setNewMemoryKey(e.target.value)}
                    placeholder="e.g., preferred_route"
                    className="w-full bg-slate-950 border border-slate-700/60 rounded-xl px-3.5 py-2.5 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
                  />
                </div>
                <div>
                  <label className="block text-xs font-medium text-slate-400 mb-1">Memory Value</label>
                  <input
                    type="text"
                    value={newMemoryValue}
                    onChange={(e) => setNewMemoryValue(e.target.value)}
                    placeholder="e.g., Highway 101 bypass"
                    className="w-full bg-slate-950 border border-slate-700/60 rounded-xl px-3.5 py-2.5 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
                  />
                </div>
                <button
                  type="submit"
                  className="w-full bg-indigo-600 hover:bg-indigo-500 text-white font-medium py-2.5 rounded-xl text-sm transition-colors shadow-lg shadow-indigo-600/30"
                >
                  Save to Persistent Memory
                </button>
              </form>
            </div>

            <div className="lg:col-span-2 bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
              <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                <Database className="w-4 h-4 text-indigo-400" /> Stored Facts & Preferences (Feature #41)
              </h3>
              <div className="space-y-3">
                {memories.map(mem => (
                  <div key={mem.id} className="p-4 bg-slate-950/60 border border-slate-800/80 rounded-xl flex items-center justify-between">
                    <div>
                      <div className="flex items-center space-x-2">
                        <span className="font-mono text-xs bg-indigo-500/20 text-indigo-400 px-2 py-0.5 rounded border border-indigo-500/30">
                          {mem.key}
                        </span>
                        <span className="text-xs text-slate-400">Category: {mem.category}</span>
                      </div>
                      <div className="text-sm font-medium text-slate-100 mt-1">{mem.value}</div>
                    </div>
                    <div className="text-xs text-slate-500">Updated: {mem.updatedAt}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {activeTab === 'engines' && (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur space-y-5">
              <h3 className="font-semibold text-slate-200 flex items-center gap-2">
                <Cpu className="w-4 h-4 text-indigo-400" /> RAM Tier & Model Selection
              </h3>

              <div>
                <label className="block text-xs font-medium text-slate-400 mb-2">Device RAM Tier (Feature #73)</label>
                <div className="grid grid-cols-3 gap-3">
                  {[
                    { id: '8GB_HIGH', label: '8GB+ High Tier' },
                    { id: '6GB_MEDIUM', label: '6GB Medium' },
                    { id: '4GB_LOW', label: '4GB Low Tier' }
                  ].map(tier => (
                    <button
                      key={tier.id}
                      onClick={() => setRamTier(tier.id as any)}
                      className={`p-3 rounded-xl border text-xs font-medium transition-all ${
                        ramTier === tier.id 
                          ? 'bg-indigo-600 text-white border-indigo-500 shadow-lg shadow-indigo-600/30' 
                          : 'bg-slate-950 text-slate-300 border-slate-800 hover:border-slate-700'
                      }`}
                    >
                      {tier.label}
                    </button>
                  ))}
                </div>
              </div>

              <div>
                <label className="block text-xs font-medium text-slate-400 mb-2">Quantization Profile (Feature #74)</label>
                <select
                  value={quantizationType}
                  onChange={(e) => setQuantizationType(e.target.value)}
                  className="w-full bg-slate-950 border border-slate-700/60 rounded-xl px-3.5 py-2.5 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
                >
                  <option>INT8 (Optimal Balance)</option>
                  <option>INT4 (Low Footprint)</option>
                  <option>FP16 (High Precision)</option>
                </select>
              </div>
            </div>

            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur space-y-5">
              <h3 className="font-semibold text-slate-200 flex items-center gap-2">
                <Layers className="w-4 h-4 text-indigo-400" /> Modular Speech Engines (Feature #79)
              </h3>

              <div>
                <label className="block text-xs font-medium text-slate-400 mb-2">STT Engine</label>
                <select
                  value={selectedSttEngine}
                  onChange={(e) => setSelectedSttEngine(e.target.value)}
                  className="w-full bg-slate-950 border border-slate-700/60 rounded-xl px-3.5 py-2.5 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
                >
                  <option>SherpaOnnxSttEngine (WenetConformer)</option>
                  <option>SherpaOnnxSttEngine (Whisper Tiny)</option>
                </select>
              </div>

              <div>
                <label className="block text-xs font-medium text-slate-400 mb-2">TTS Engine</label>
                <select
                  value={selectedTtsEngine}
                  onChange={(e) => setSelectedTtsEngine(e.target.value)}
                  className="w-full bg-slate-950 border border-slate-700/60 rounded-xl px-3.5 py-2.5 text-sm text-slate-100 focus:outline-none focus:border-indigo-500"
                >
                  <option>SherpaOnnxTtsEngine (VITS Piper)</option>
                  <option>SherpaOnnxTtsEngine (Kokoro)</option>
                </select>
              </div>

              <div className="p-3.5 bg-emerald-500/10 border border-emerald-500/30 rounded-xl flex items-center space-x-3 text-xs text-emerald-300">
                <CheckCircle className="w-5 h-5 text-emerald-400 shrink-0" />
                <span>All offline models verified and ready for execution.</span>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'diagnostics' && (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
              <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                <Activity className="w-4 h-4 text-indigo-400" /> Speech Pipeline State Machine (#78)
              </h3>
              <div className="space-y-3">
                {[
                  { state: 'IDLE', desc: 'Waiting for wake word or manual trigger' },
                  { state: 'LISTENING', desc: 'Active VAD capturing audio stream' },
                  { state: 'PROCESSING', desc: 'Running STT and Natural Language Intent Parser' },
                  { state: 'SPEAKING', desc: 'TTS output playback with barge-in protection' }
                ].map((st, i) => (
                  <div key={i} className={`p-3.5 rounded-xl border flex items-center justify-between ${
                    pipelineState === st.state 
                      ? 'bg-indigo-600/20 border-indigo-500 text-white' 
                      : 'bg-slate-950/60 border-slate-800 text-slate-400'
                  }`}>
                    <div>
                      <div className="font-mono font-bold text-sm text-indigo-300">{st.state}</div>
                      <div className="text-xs text-slate-400 mt-0.5">{st.desc}</div>
                    </div>
                    {pipelineState === st.state && (
                      <span className="w-3 h-3 bg-indigo-400 rounded-full animate-ping" />
                    )}
                  </div>
                ))}
              </div>
            </div>

            <div className="bg-slate-900/60 border border-slate-800/80 rounded-2xl p-6 shadow-xl backdrop-blur">
              <h3 className="font-semibold text-slate-200 mb-4 flex items-center gap-2">
                <ShieldCheck className="w-4 h-4 text-emerald-400" /> Error Fallback Chain (#77)
              </h3>
              <div className="space-y-3 text-sm">
                <div className="p-3.5 bg-slate-950/60 border border-slate-800 rounded-xl">
                  <div className="font-medium text-slate-200">1. STT Engine Timeout</div>
                  <div className="text-xs text-slate-400 mt-1">Fallback: Switch to lightweight offline offline-stt model.</div>
                </div>
                <div className="p-3.5 bg-slate-950/60 border border-slate-800 rounded-xl">
                  <div className="font-medium text-slate-200">2. Audio Focus Loss</div>
                  <div className="text-xs text-slate-400 mt-1">Fallback: Pause active TTS and queue pending command.</div>
                </div>
                <div className="p-3.5 bg-slate-950/60 border border-slate-800 rounded-xl">
                  <div className="font-medium text-slate-200">3. Low RAM Warning</div>
                  <div className="text-xs text-slate-400 mt-1">Fallback: Downscale quantization profile from FP16 to INT4.</div>
                </div>
              </div>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

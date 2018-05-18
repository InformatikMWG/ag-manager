

\LoadClass{scrartcl}


\usepackage[margin=2.5cm]{geometry}
\usepackage{scrlayer-scrpage}


\RequirePackage{iftex}
\ifLuaTeX\else
    \ClassError{courser-credcards}{LuaLaTeX is required to compile this document.}{Please switch the LaTeX engine.}
    \stop
\fi

\RequirePackage{array}
\RequirePackage{graphicx}
\RequirePackage{graphbox}
\RequirePackage{longtable}
\RequirePackage{pgfkeys}
\RequirePackage{setspace}

\PassOptionsToPackage{table}{xcolor}
\RequirePackage{xcolor}


\RequirePackage{fitnames}

\RequirePackage{fontspec}
\setmainfont[Numbers={Lining,Tabular}]{Fira Sans}
\setsansfont[Numbers={Lining,Tabular}]{Fira Sans}
\setmonofont{Fira Mono}


\RequirePackage{fontawesome}


\RequirePackage{polyglossia}
\setdefaultlanguage{german}

\PassOptionsToPackage{german}{selnolig}
\RequirePackage{selnolig}


\PassOptionsToPackage{pdfborder={0 0 0}, unicode}{hyperref}
\RequirePackage{hyperref}


\pagestyle{scrheadings}
% 
\setkomafont{pageheadfoot}{\footnotesize\rmfamily}
% \ifoot{}
\cfoot{}
% \chead{}
\ifoot{\timestamp}
\ofoot{\thepage}



\newcount\hour
\newcount\minute
\hour = \time
\divide\hour by 60
\minute = \hour
\multiply\minute by 60
\advance\minute by -\time
\minute = -\minute

\edef\timestamp{%
    \ifnum\day<10{0}\fi\the\day.%
    \ifnum\month<10{0}\fi\the\month.%
    \the\year,~%
    %
    \ifnum\hour<10{0}\fi\the\hour:%
    \ifnum\minute<10{0}\fi\the\minute
}


\newcount\listindex
\newcount\lastrowodd

\newsavebox{\courserreg@pnumbox}
\newlength{\courserreg@pnameboxwd}

\def\arraystretch{1.3}

\parindent0pt

\definecolor{MWGRed}{RGB}{100,29,27}


\AtBeginDocument{%
    \hypersetup{pdfcreator={Courser LuaLaTeX Output (\timestamp)}}
    \thispagestyle{empty}
    \pagenumbering{Roman}
    \begin{center}
        Courser Output \\
        Generated: \timestamp
    \end{center}
    
    \def\contentsname{Index}
    \tableofcontents
    \clearpage
    \pagenumbering{arabic}
    \setcounter{page}{1}}


\def\regentry#1#2#3{%
    % check wheter the previous row was odd (1 = true, 0 = false)
    % We have to check here because using \relax (which is necessary for calculation)
    % before printing contents to the table makes LaTeX throw “Misplaced \noalign” errors
    \ifnum\lastrowodd=1\rowcolor{MWGRed!17}\fi%
    \global\advance\listindex 1\relax%
    %
    % calculation:  \listindex % 2
    \global\lastrowodd=\listindex\relax%
    \global\divide\lastrowodd by 2\relax%
    \global\multiply\lastrowodd by 2\relax%
    \global\multiply\lastrowodd by -1\relax%
    \global\advance\lastrowodd by \listindex\relax%
    %
    \the\listindex & \fitname{#1}{#2}{.8\textwidth} (#3) \\\hline}


\def\courserreg@pnr{N/A}
\def\courserreg@ptitle{N/A}
\def\courserreg@pprov{N/A}
\def\courserreg@pdescr{\textup{N/A}}
\def\courserreg@datetime{N/A}
\def\courserreg@location{N/A}
\def\courserreg@participants{N/A}
\def\courserreg@maxparticipants{N/A}
\def\courserreg@attfee{N/A}
\let\courserreg@supervisor\@empty



\pgfkeys{%
    /courserproj/projnr/.cd, .value required,
        .store in=\courserreg@pnr,
    /courserproj/title/.cd, .value required,
        .store in=\courserreg@ptitle,
    /courserproj/provider/.cd, .value required,
        .store in=\courserreg@pprov,
    /courserproj/descr/.cd, .value required,
        .store in=\courserreg@pdescr,
    /courserproj/datetime/.cd, .value required,
        .store in=\courserreg@datetime,
    /courserproj/location/.cd, .value required,
        .store in=\courserreg@location,
    /courserproj/participants/.cd, .value required,
        .store in=\courserreg@participants,
    /courserproj/maxparticipants/.cd, .value required,
        .store in=\courserreg@maxparticipants,
    /courserproj/attfee/.cd, .value required,
        .store in=\courserreg@attfee,
    /courserproj/supervisor/.cd, .value required,
        .store in=\courserreg@supervisor,
}


\newenvironment{register}[1]{%
    \clearpage%
    \pgfkeys{/courserproj/.cd, #1}
    
    \fitnamesconfig{lastnamefirst=false, lastnamefont=sc, firstname=firstletter}
    \begin{minipage}[t]{.825\linewidth}
        \phantomsection%
        \sbox{\courserreg@pnumbox}{\textbf{\LARGE\scshape\courserreg@pnr.~~}}%
        \setlength{\courserreg@pnameboxwd}{.95\linewidth}%
        \addtolength{\courserreg@pnameboxwd}{-\wd\courserreg@pnumbox}%
        
        
        \usebox{\courserreg@pnumbox}%
        \begin{minipage}[t]{\courserreg@pnameboxwd}%
            \setstretch{1.35}\flushleft%
            \textbf{\LARGE\scshape\courserreg@ptitle}%
        \end{minipage}
        \addcontentsline{toc}{subsection}{\courserreg@pnr.~~\courserreg@ptitle}
        
        \smallskip
        \begin{center}
            \begin{tabular}{*{2}{cp{.4\linewidth}}}
                \faUser         & \multicolumn{3}{p{.8\linewidth}}{\courserreg@pprov} \\
                \faInfo         & \multicolumn{3}{p{.8\linewidth}}{\itshape\courserreg@pdescr} \\
                %
                \faCalendar     & \multicolumn{3}{p{.8\linewidth}}{\courserreg@datetime} \\
                \faMapMarker    & \multicolumn{3}{p{.8\linewidth}}{\courserreg@location} \\
                \faUsers        & \courserreg@participants\quad(\courserreg@maxparticipants) &
                \faMoney        & \courserreg@attfee \\
                \ifx\courserreg@supervisor\@empty\else%
                  \faBinoculars & \multicolumn{3}{p{.8\linewidth}}{\courserreg@supervisor}
                \fi%
            \end{tabular}
        \end{center}
    \end{minipage}
    \begin{minipage}[t]{.175\linewidth}
        \includegraphics[align=t, width=\linewidth]{mwglogo}
    \end{minipage}
    
    
    \global\listindex=0\relax%
    \global\lastrowodd=0\relax%
    \fitnamesconfig{lastnamefirst, lastnamefont=sc, firstname=full}%
    \longtable{|>{\hfill}p{.05\textwidth}|p{.9\textwidth}|}%
        \hline%
        \rowcolor{MWGRed}
        \color{white}\textbf{Nr.} & \color{white}\textbf{Name} \\\hline\hline\hline%
        \endfirsthead%
        %
        \multicolumn{2}{r}{\footnotesize\textit{(continued)}} \\\hline%
        \rowcolor{MWGRed}
        \color{white}\textbf{Nr.} & \color{white}\textbf{Name} \\\hline\hline\hline%
        \endhead%
        %
}{%
    \endlongtable%
}
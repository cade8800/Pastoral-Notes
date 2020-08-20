declare var editormd: any;
import { FileUpload } from './constants';

export const EditorMDConfig = {
    mode: 'markdown',          // gfm or markdown
    name: '',             // Form element name for post
    value: '',             // value for CodeMirror, if mode not gfm/markdown


    theme: 'dark',             // Editor.md self themes, before v1.5.0 is CodeMirror theme, default empty
    editorTheme: 'pastel-on-dark',      // Editor area, this is CodeMirror theme at v1.5.0
    previewTheme: 'default',             // Preview area theme, default empty


    markdown: '',             // Markdown source code
    appendMarkdown: '',             // if in init textarea value not empty, append markdown to textarea
    width: '100%',
    height: '600px',
    path: '/assets/editor.md/lib/',       // Dependents module file directory
    pluginPath: '/assets/editor.md/plugins/',             // If this empty, default use settings.path + '../plugins/'
    delay: 300,            // Delay parse markdown to html, Uint : ms
    autoLoadModules: true,           // Automatic load dependent module files
    watch: true,
    placeholder: '从这里开始写正文',
    gotoLine: true,           // Enable / disable goto a line
    codeFold: false,
    autoHeight: false,
    autoFocus: true,           // Enable / disable auto focus editor left input area
    autoCloseTags: true,
    searchReplace: true,           // Enable / disable (CodeMirror) search and replace function
    syncScrolling: true,           // options: true | false | 'single', default true
    readOnly: false,          // Enable / disable readonly mode
    tabSize: 4,
    indentUnit: 4,
    lineNumbers: true,           // Display editor line numbers
    lineWrapping: true,
    autoCloseBrackets: true,
    showTrailingSpace: true,
    matchBrackets: true,
    indentWithTabs: true,
    styleSelectedText: true,
    matchWordHighlight: true,           // options: true, false, 'onselected'
    styleActiveLine: true,           // Highlight the current line
    dialogLockScreen: true,
    dialogShowMask: true,
    dialogDraggable: true,
    dialogMaskBgColor: '#fff',
    dialogMaskOpacity: 0.1,
    fontSize: '13px',
    saveHTMLToTextarea: false,          // If enable, Editor will create a <textarea name='{editor-id}-html-code'> tag save HTML code for form post to server-side.
    disabledKeyMaps: [],

    onload() { },
    onresize() { },
    onchange() { },
    onwatch: null,
    onunwatch: null,
    onpreviewing() { },
    onpreviewed() { },
    onfullscreen() { },
    onfullscreenExit() { },
    onscroll() { },
    onpreviewscroll() { },

    imageUpload: true,          // Enable/disable upload
    imageFormats: ['jpg', 'jpeg', 'gif', 'png', 'bmp', 'webp'],
    imageUploadURL: FileUpload,             // Upload url
    crossDomainUpload: true,          // Enable/disable Cross-domain upload
    uploadCallbackURL: '',             // Cross-domain upload callback url

    toc: false,           // Table of contents
    tocm: false,          // Using [TOCM], auto create ToC dropdown menu
    tocTitle: '目录',             // for ToC dropdown menu button
    tocDropdown: false,          // Enable/disable Table Of Contents dropdown menu
    tocContainer: '#custom_toc_container',             // Custom Table Of Contents Container Selector
    tocStartLevel: 1,              // Said from H1 to create ToC
    htmlDecode: 'style,script,iframe',          // Open the HTML tag identification 
    pageBreak: true,           // Enable parse page break [========]
    atLink: true,           // for @link
    emailLink: true,           // for email address auto link
    taskList: true,          // Enable Github Flavored Markdown task lists

    emoji: true,          // :emoji: , Support Github emoji, Twitter Emoji (Twemoji);
    // Support FontAwesome icon emoji :fa-xxx: > Using fontAwesome icon web fonts;
    // Support Editor.md logo icon emoji :editormd-logo: :editormd-logo-1x: > 1~8x;
    emojiCategories: [               // Custom Emoji categories
        // 'github-emoji',
        'twemoji',
        'font-awesome',
        // 'editormd-logo'
    ],


    tex: true,          // TeX(LaTeX), based on KaTeX
    flowChart: true,          // flowChart.js only support IE9+
    sequenceDiagram: true,          // sequenceDiagram.js only support IE9+
    previewCodeHighlight: true,           // Enable / disable code highlight of editor preview area

    toolbar: true,           // show or hide toolbar
    toolbarAutoFixed: true,           // on window scroll auto fixed position
    toolbarIcons() {
        return ['undo', 'redo', '|', 'bold', 'del', 'italic', 'quote', 'ucwords', 'uppercase', 'lowercase', '|', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', '|', 'list-ul', 'list-ol', 'hr', '|', 'link', 'reference-link', 'image', 'code', 'preformatted-text', 'code-block', 'table', 'datetime', 'emoji', 'html-entities', 'pagebreak', '|', 'goto-line', 'watch', 'preview', 'fullscreen', 'clear', 'search'];
    },         // Toolbar icons mode, options: full, simple, mini, See `editormd.toolbarModes` property.
    // toolbarTitles: {},
    // toolbarHandlers: {
    //     ucwords() {
    //         return editormd.toolbarHandlers.ucwords;
    //     },
    //     lowercase() {
    //         return editormd.toolbarHandlers.lowercase;
    //     }
    // },
    // toolbarCustomIcons: {               // using html tag create toolbar icon, unused default <a> tag.
    //     lowercase: '<a href=\'javascript:;\' title=\'Lowercase\' unselectable=\'on\'><i class=\'fa\' name=\'lowercase\' style=\'font-size:24px;margin-top: -10px;\'>a</i></a>',
    //     ucwords: '<a href=\'javascript:;\' title=\'ucwords\' unselectable=\'on\'><i class=\'fa\' name=\'ucwords\' style=\'font-size:20px;margin-top: -3px;\'>Aa</i></a>'
    // },
    // toolbarIconTexts: {},

    // lang: {  // Language data, you can custom your language.
    //     name: 'zh-cn',
    //     description: '开源在线Markdown编辑器<br/>Open source online Markdown editor.',
    //     tocTitle: '目录',
    //     toolbar: {
    //         // ...
    //     },
    //     button: {
    //         // ...
    //     },
    //     dialog: {
    //         // ...
    //     }
    //     // ...
    // }
};

 tinymce.init({
  selector: "#postEditor",
  plugins: "lists",
  inline: true,
  toolbar_location: "bottom",
  language: "pt_BR",
  fontsize_formats: "8px 12px 16px 20px 24px 28px 32px 36px",
  menubar: false,
  skin: 'oxide-dark',
  toolbar: "undo redo | removeformat bold italic underline strikethrough superscript subscript | alignleft aligncenter alignright alignjustify | fontsizeselect styleselect",
  style_formats: [{
    title: 'Headings', 
    items: [
      { title: 'Heading 1', format: 'h3' },
      { title: 'Heading 2', format: 'h4' },
      { title: 'Heading 3', format: 'h5' },
      { title: 'Heading 4', format: 'h6' }
    ]}, {
    title: 'Blocks',
    items: [
      { title: 'Paragraph', format: 'p' },
      { title: 'Destaque', format: 'blockquote' },
      { title: 'Border', format: 'div' },
      { title: 'Pre', format: 'pre' }
    ]}]
  });
  
 
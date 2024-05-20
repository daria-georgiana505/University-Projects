using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;

namespace web_asp.Enums
{
    public enum DocumentFormat
    {
        [Display(Name = "Plain Text")]
        PlainText,
        [Display(Name = "Rich Text Format")]
        RichTextFormat,
        [Display(Name = "Markdown")]
        Markdown,
        [Display(Name = "Microsoft Word")]
        MicrosoftWord,
        [Display(Name = "Open Document Text")]
        OpenDocumentText,
        [Display(Name = "PDF")]
        PDF,
        [Display(Name = "ePub")]
        ePub,
        [Display(Name = "HTML")]
        HTML,
        [Display(Name = "JPEG")]
        JPEG,
        [Display(Name = "PNG")]
        PNG,
        [Display(Name = "GIF")]
        GIF,
        [Display(Name = "ZIP")]
        ZIP,
        [Display(Name = "RAR")]
        RAR
    }
}

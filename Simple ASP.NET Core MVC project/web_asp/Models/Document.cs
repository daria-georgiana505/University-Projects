using Microsoft.EntityFrameworkCore;
using web_asp.Enums;

namespace web_asp.Models
{
    [PrimaryKey(nameof(Id))]
    public class Document
    {
        public int Id { get; set; }
        public string Author { get; set; } = string.Empty;
        public string Title { get; set; } = string.Empty;
        public int NrPages { get; set; } = 0;
        public DocumentType Type { get; set; }
        public DocumentFormat Format { get; set; }
    }
}

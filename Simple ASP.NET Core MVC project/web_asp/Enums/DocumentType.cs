using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;

namespace web_asp.Enums
{
    public enum DocumentType
    {
        [Display(Name = "Technical")]
        Technical,
        [Display(Name = "Business")]
        Business,
        [Display(Name = "Legal")]
        Legal,
        [Display(Name = "Academic")]
        Academic,
        [Display(Name = "Marketing")]
        Marketing,
        [Display(Name = "Project Management")]
        ProjectManagement
    }
}

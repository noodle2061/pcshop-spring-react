import { Grid, Link, Typography } from '@mui/material';

const footerSections = [
  {
    title: 'Company',
    items: ['About', 'Blog', 'Jobs', 'Press', 'Partners'],
  },
  {
    title: 'Solutions',
    items: ['Marketing', 'Analytics', 'Commerce', 'Insights', 'Support'],
  },
  {
    title: 'Documentation',
    items: ['Guides', 'API Status'],
  },
  {
    title: 'Legal',
    items: ['Claim', 'Privacy', 'Terms'],
  },
];

const Footer = () => {
  return (
      <Grid
          className='bg-black text-white mt-10 text-center' container color={'white' } sx={{ bgcolor: 'black', color: 'white', py: 3 }}
      >
        {footerSections.map((section, index) => (
            <Grid key={index} item xs={12} sm={6} md={3}>
              <Typography className="pb-5" variant="h6" gutterBottom>
                {section.title}
              </Typography>
              {section.items.map((item, idx) => (
                  <Typography key={idx} variant="body2" component="p" gutterBottom>
                    {item}
                  </Typography>
              ))}
            </Grid>
        ))}
        <Grid item xs={12} sx={{ pt: 5 }}>
          <Typography variant="body2" align="center">
            &copy; 2023 My Company. All rights reserved. Made with love by Me.
          </Typography>
          <Typography variant="body2" align="center">
            Icons made by{' '}
            <Link href="https://www.freepik.com" color="inherit" underline="always">
              Freepik
            </Link>{' '}
            from{' '}
            <Link href="https://www.flaticon.com/" color="inherit" underline="always">
              www.flaticon.com
            </Link>
          </Typography>
        </Grid>
      </Grid>
  );
};

export default Footer;
